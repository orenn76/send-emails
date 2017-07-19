package com.ninyo.send.emails.worker;

import com.ninyo.send.emails.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class EmailSenderImpl implements EmailSender {

    @Async
    public void sendEmail(User user) {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
            log.info("Email was successfully sent to the recipient with email: {}, first name: {}, last name: {}", user.getEmail(), user.getFirstName(), user.getLastName());
        } catch (InterruptedException e) {
            log.error("An error occurred: " + e.getMessage(), e);
        }
    }
}
