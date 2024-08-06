package com.poly.coffee.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "service-discovery.send-email")
public class TemplateProperties {
    private String fromAddress;
    private final Template templateName = new Template();

    @Data
    public static class Template {
        private String createAccount;
        private String forgotPassword;
        private String confirmPayment;
    }
}
