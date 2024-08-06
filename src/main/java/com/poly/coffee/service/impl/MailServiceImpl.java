package com.poly.coffee.service.impl;

import com.poly.coffee.constant.Constant;
import com.poly.coffee.dto.request.MailRequest;
import com.poly.coffee.dto.response.MailResponse;
import com.poly.coffee.entity.User;
import com.poly.coffee.enums.EmailTypeEnum;
import com.poly.coffee.property.TemplateProperties;
import com.poly.coffee.repository.UserRepository;
import com.poly.coffee.service.MailService;
import com.poly.coffee.util.PasswordGeneratorUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class MailServiceImpl implements MailService {

    JavaMailSender sender;

    Configuration config;

    TemplateProperties templateProperties;

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    @Override
    public MailResponse sendEmail(MailRequest request) {
        MailResponse response = new MailResponse();
        MimeMessage message = sender.createMimeMessage();

        try {
            // Set media type
            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Template template = config.getTemplate(request.getTemplateName());
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, request.getModel());

            helper.setTo(request.getTo());
            helper.setText(html, true);
            helper.setSubject(request.getSubject());
            helper.setFrom(request.getFrom());

            sender.send(message);

            response.setMessage("Email sent to: " + request.getTo());
            response.setStatus(Boolean.TRUE);
        } catch (MessagingException | IOException | TemplateException e) {
            response.setMessage("Email sent failed with error: " + e.getMessage());
            response.setStatus(Boolean.FALSE);
        }

        return response;
    }

    @Override
    public MailResponse sendCreateAccount(User user) {
        MailRequest request = this.createDataMailRequest(EmailTypeEnum.CREATE_ACCOUNT, user);
        return this.sendEmail(request);
    }

    @Override
    public MailResponse sendForgotPassword(User user) {
        MailRequest request = this.createDataMailRequest(EmailTypeEnum.FORGOT_PASSWORD, user);
        return this.sendEmail(request);
    }

    @Override
    public MailResponse sendConfirmPayment(User user, Map<String, Object> items) {
        MailRequest request = this.createDataMailRequest(EmailTypeEnum.CONFIRM_PAYMENT, user, items);
        return this.sendEmail(request);
    }

    private MailRequest createDataMailRequest(EmailTypeEnum emailType, User user) {
        return createDataMailRequest(emailType, user, null);
    }

    private MailRequest createDataMailRequest(EmailTypeEnum emailType, User user, Map<String, Object> items) {
        String templateName = "";
        String subject = "";
        String fromEmail = templateProperties.getFromAddress();
        String toEmail = user.getEmail();

        Map<String, Object> model = new HashMap<>();

        String fullName = user.getFirstName().trim() + " " + user.getLastName().trim();
        model.put(Constant.EmailTemplateData.NAME_KEY, fullName);

        if (EmailTypeEnum.CREATE_ACCOUNT == emailType) {
            templateName = templateProperties.getTemplateName().getCreateAccount();
            subject = Constant.EmailSubject.CREATE_ACCOUNT_SUBJECT;
        } else if (EmailTypeEnum.FORGOT_PASSWORD == emailType) {
            templateName = templateProperties.getTemplateName().getForgotPassword();
            subject = Constant.EmailSubject.FORGOT_PASSWORD_SUBJECT;

            String randomPassword = PasswordGeneratorUtil.generateRandomPassword(8);

            user.setPassword(passwordEncoder.encode(randomPassword));

            userRepository.save(user);

            model.put(Constant.EmailTemplateData.EMAIL_KEY, user.getEmail());
            model.put(Constant.EmailTemplateData.PASSWORD_KEY, randomPassword);
        } else if (EmailTypeEnum.CONFIRM_PAYMENT == emailType) {
            templateName = templateProperties.getTemplateName().getConfirmPayment();
            subject = Constant.EmailSubject.CONFIRM_PAYMENT_SUBJECT;

            model.putAll(items);
        }

        return MailRequest.builder()
                .from(fromEmail)
                .to(toEmail)
                .subject(subject)
                .model(model)
                .templateName(templateName)
                .build();
    }
}
