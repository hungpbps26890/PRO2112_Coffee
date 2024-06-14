package com.poly.coffee.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1001, "Username already existed", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1001, "Email already existed", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1002, "User not found", HttpStatus.NOT_FOUND),
    USER_NOT_EXISTED(1003, "Username is not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1004, "Username or password is not valid", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1006, "You do not have permission", HttpStatus.FORBIDDEN),
    ROLE_NOT_FOUND(1008, "Role not found", HttpStatus.NOT_FOUND),
    INVALID_DOB(1009, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1005, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_CHANGE_PASSWORD(1006, "Password is invalid", HttpStatus.BAD_REQUEST),
    INVALID_CONFIRMED_PASSWORD(1007, "Confirmed Password is not match", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(2001, "Category not found", HttpStatus.NOT_FOUND),
    NOT_EMPTY_CATEGORY_NAME(2002, "Category name must be not empty", HttpStatus.BAD_REQUEST),
    DRINK_NOT_FOUND(3001, "Drink not found", HttpStatus.NOT_FOUND),
    TOPPING_NOT_FOUND(4001, "Topping not found", HttpStatus.NOT_FOUND),
    SIZE_NOT_FOUND(5001, "Size not found", HttpStatus.NOT_FOUND),
    CART_ITEM_NOT_FOUND(6001, "Cart item not found", HttpStatus.NOT_FOUND),
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
