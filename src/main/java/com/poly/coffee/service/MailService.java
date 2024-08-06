package com.poly.coffee.service;

import com.poly.coffee.dto.request.MailRequest;
import com.poly.coffee.dto.response.MailResponse;
import com.poly.coffee.entity.User;

import java.util.Map;

public interface MailService {

    MailResponse sendEmail(MailRequest request);

    MailResponse sendCreateAccount(User user);

    MailResponse sendForgotPassword(User user);

    MailResponse sendConfirmPayment(User user, Map<String, Object> items);
}
