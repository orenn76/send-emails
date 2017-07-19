package com.ninyo.send.emails.worker;

import com.ninyo.send.emails.model.User;

public interface EmailSender {

    void sendEmail(User user);
}
