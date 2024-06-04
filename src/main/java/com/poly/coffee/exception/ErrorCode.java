package com.poly.coffee.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized exception"),
    USER_EXISTED(1001, "User existed"),
    USER_NOT_FOUND(1002, "User not found"),
    USER_NOT_EXISTED(1003, "User not existed"),
    INVALID_PASSWORD(1004, "Password must be at least 8 characters"),
    UNAUTHENTICATED(1005, "User not existed"),
    CATEGORY_NOT_FOUND(2001, "Category not found"),
    NOT_EMPTY_CATEGORY_NAME(2002, "Category name must be not empty"),
    DRINK_NOT_FOUND(3001, "Drink not found"),
    TOPPING_NOT_FOUND(4001, "Topping not found"),
    SIZE_NOT_FOUND(5001, "Size not found"),
    ;

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
