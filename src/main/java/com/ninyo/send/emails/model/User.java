package com.ninyo.send.emails.model;

import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class User {

    private String email;
    private String firstName;
    private String lastName;
}
