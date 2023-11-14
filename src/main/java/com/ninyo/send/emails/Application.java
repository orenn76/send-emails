package com.ninyo.send.emails;

import com.ninyo.send.emails.service.DaoService;
import com.ninyo.send.emails.service.EmailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException, SQLException, ParseException {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        EmailService emailService = ctx.getBean(EmailService.class);
        emailService.sendEmails();

        DaoService daoService = ctx.getBean(DaoService.class);
        daoService.testDAO();

        ((ConfigurableApplicationContext)ctx).close();
    }
}
