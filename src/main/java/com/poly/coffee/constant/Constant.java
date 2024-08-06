package com.poly.coffee.constant;

import lombok.experimental.UtilityClass;

public class Constant {

    @UtilityClass
    public static class EmailTemplateData {
        public String NAME_KEY = "name";
        public String EMAIL_KEY = "email";
        public String PASSWORD_KEY = "password";
        public String TRANSACTION_NO_KEY = "transaction_no";
        public String BANK_CODE_KEY = "bank_code";
        public String AMOUNT_KEY = "amount";
        public String ORDER_INFO_KEY = "order_info";
        public String PAY_DATE_KEY = "pay_date";
    }

    @UtilityClass
    public static class EmailSubject {
        public String CREATE_ACCOUNT_SUBJECT = "May Coffee Register Email";
        public String FORGOT_PASSWORD_SUBJECT = "May Coffee Forgot Password Email";
        public String CONFIRM_PAYMENT_SUBJECT = "May Coffee Confirm Payment Email";
    }
}
