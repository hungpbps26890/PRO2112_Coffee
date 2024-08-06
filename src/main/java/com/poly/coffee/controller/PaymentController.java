package com.poly.coffee.controller;

import com.poly.coffee.constant.Constant;
import com.poly.coffee.constant.StatusCode;
import com.poly.coffee.dto.request.UpdateOrderStatusRequest;
import com.poly.coffee.dto.response.ApiResponse;
import com.poly.coffee.dto.response.OrderResponse;
import com.poly.coffee.dto.response.UserResponse;
import com.poly.coffee.dto.response.VNPayResponse;
import com.poly.coffee.entity.OrderStatus;
import com.poly.coffee.entity.User;
import com.poly.coffee.mapper.UserMapper;
import com.poly.coffee.service.MailService;
import com.poly.coffee.service.OrderService;
import com.poly.coffee.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    PaymentService paymentService;

    OrderService orderService;

    UserMapper userMapper;

    MailService mailService;

    @GetMapping("/vn-pay")
    public ApiResponse<VNPayResponse> pay(HttpServletRequest request) {
        return ApiResponse.<VNPayResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("VNPay Payment Process Successfully")
                .result(paymentService.createVNPayPayment(request))
                .build();
    }

    @GetMapping("/vn-pay-callback")
    public void payCallbackHandler(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        String status = request.getParameter("vnp_ResponseCode");
        String transactionNo = request.getParameter("vnp_TransactionNo");
        String bankCode = request.getParameter("vnp_BankCode");
        long amount = Long.parseLong(request.getParameter("vnp_Amount")) / 100L;
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String payDate = request.getParameter("vnp_PayDate");

        // Định dạng ban đầu của chuỗi
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        // Định dạng mong muốn
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        // Phân tích chuỗi thành đối tượng Date
        Date date = originalFormat.parse(payDate);

        // Định dạng lại Date thành chuỗi với định dạng mong muốn
        String formattedDate = targetFormat.format(date);

        long orderId = Integer.parseInt(request.getParameter("orderId"));

        OrderResponse orderResponse = orderService.getOrderById(orderId);
        UserResponse userResponse = orderResponse.getUser();
        User user = userMapper.userResponseToUser(userResponse);

        if (status.equals("00")) {
            UpdateOrderStatusRequest updateOrderStatusRequest = UpdateOrderStatusRequest.builder()
                    .id(orderId)
                    .orderStatus(OrderStatus.builder().id(1L).build())
                    .paymentStatus(true).build();
            orderService.updateOrderStatus(updateOrderStatusRequest);

            System.out.println("Thong tin VNPay: " + transactionNo + " " + bankCode + " " + amount + " " + orderInfo + " " + formattedDate);

            Map<String, Object> items = new HashMap<>();
            items.put(Constant.EmailTemplateData.TRANSACTION_NO_KEY, transactionNo);
            items.put(Constant.EmailTemplateData.BANK_CODE_KEY, bankCode);
            items.put(Constant.EmailTemplateData.AMOUNT_KEY, amount);
            items.put(Constant.EmailTemplateData.ORDER_INFO_KEY, orderInfo);
            items.put(Constant.EmailTemplateData.PAY_DATE_KEY, formattedDate);

            mailService.sendConfirmPayment(user, items);

            response.sendRedirect("http://localhost:2000/order");
        } else {
            response.sendRedirect("http://localhost:2000/order");
        }
    }
}
