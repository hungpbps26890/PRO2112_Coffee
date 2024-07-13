package com.poly.coffee.service.impl;

import com.poly.coffee.config.VNPAYConfig;
import com.poly.coffee.dto.response.VNPayResponse;
import com.poly.coffee.service.PaymentService;
import com.poly.coffee.util.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private VNPAYConfig vnPayConfig;

    @Value("${payment.vnPay.returnUrl}")
    private String vnp_ReturnUrl;

    @Override
    public VNPayResponse createVNPayPayment(HttpServletRequest request) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        long orderId = Integer.parseInt(request.getParameter("orderId"));

        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));

        vnpParamsMap.put("vnp_ReturnUrl", this.vnp_ReturnUrl + "?orderId=" + orderId);

        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }

        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));

        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);

        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);

        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;

        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;

        return VNPayResponse.builder().paymentUrl(paymentUrl).build();
    }
}
