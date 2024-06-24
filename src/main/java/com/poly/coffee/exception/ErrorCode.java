package com.poly.coffee.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1001, "User already existed", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1002, "Email already existed", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1003, "User not found", HttpStatus.NOT_FOUND),
    USER_NOT_EXISTED(1004, "User is not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1005, "Email or password is not valid", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1006, "You do not have permission", HttpStatus.FORBIDDEN),
    ROLE_NOT_FOUND(1007, "Role not found", HttpStatus.NOT_FOUND),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1009, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_CHANGE_PASSWORD(1010, "Current Password is invalid", HttpStatus.BAD_REQUEST),
    INVALID_CONFIRMED_PASSWORD(1011, "Confirmed Password is not match", HttpStatus.BAD_REQUEST),
    ACCOUNT_INACTIVE(1012, "Your account is blocked", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(2001, "Category not found", HttpStatus.NOT_FOUND),
    NOT_EMPTY_CATEGORY_NAME(2002, "Category name must be not empty", HttpStatus.BAD_REQUEST),
    DRINK_NOT_FOUND(3001, "Drink not found", HttpStatus.NOT_FOUND),
    TOPPING_NOT_FOUND(4001, "Topping not found", HttpStatus.NOT_FOUND),
    SIZE_NOT_FOUND(5001, "Size not found", HttpStatus.NOT_FOUND),
    CART_ITEM_NOT_FOUND(6001, "Cart item not found", HttpStatus.NOT_FOUND),
    PAYMENT_METHOD_NOT_FOUND(7001, "Payment method not found", HttpStatus.NOT_FOUND),
    ORDER_NOT_NOT_FOUND(7002, "Order not found", HttpStatus.NOT_FOUND),
    ORDER_STATUS_NOT_NOT_FOUND(7003, "Order status not found", HttpStatus.NOT_FOUND),
    ;

    private final int code;
    private final String message;
    private final HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
