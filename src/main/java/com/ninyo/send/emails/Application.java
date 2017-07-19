package com.ninyo.send.emails;

import com.ninyo.send.emails.service.EmailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        EmailService emailService = ctx.getBean(EmailService.class);
        emailService.sendEmails();
        ((ConfigurableApplicationContext)ctx).close();
    }
}
