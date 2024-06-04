package com.poly.coffee.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized exception"),
    USER_EXISTED(1001, "User existed"),
    USER_NOT_FOUND(1002, "User not found"),
    USER_NOT_EXISTED(1002, "User not existed"),
    INVALID_PASSWORD(1003, "Password must be at least 8 characters"),
    CATEGORY_NOT_FOUND(1004, "Category not found"),
    NOT_EMPTY_CATEGORY_NAME(1005, "Category name must be not empty"),
    DRINK_NOT_FOUND(1006, "Drink not found"),
    TOPPING_NOT_FOUND(1007, "Topping not found"),
    SIZE_NOT_FOUND(1008, "Size not found"),
    ;

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
