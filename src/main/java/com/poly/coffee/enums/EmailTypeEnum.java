package com.poly.coffee.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmailTypeEnum {
    CREATE_ACCOUNT(0),
    FORGOT_PASSWORD(1),
    CONFIRM_PAYMENT(2);

    private final int code;
}
