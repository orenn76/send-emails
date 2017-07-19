package com.ninyo.send.emails.service;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.ninyo.send.emails.model.User;
import com.ninyo.send.emails.worker.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.regex.Pattern;

@Service
public class EmailServiceImpl implements EmailService {

    private final static String REGEX = ";(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

    @Value("${filePath}")
    private String filePath;

    @Autowired
    private EmailSender emailSender;

    public void sendEmails() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                Iterable<String> userLine = Splitter.on(Pattern.compile(REGEX)).split(line);
                if (Iterables.size(userLine) >= 3) {
                    User user = createUser(userLine);
                    emailSender.sendEmail(user);
                }
            }
        }
    }

    private User createUser(Iterable<String> userLine) {
        Iterator it = userLine.iterator();
        String email = it.next().toString();
        String firstName = it.next().toString();
        String lastName = it.next().toString();
        return User.builder().email(email).firstName(firstName).lastName(lastName).build();
    }
}