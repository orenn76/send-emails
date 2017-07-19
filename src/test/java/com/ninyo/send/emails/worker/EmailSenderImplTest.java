package com.ninyo.send.emails.worker;

import com.ninyo.send.emails.model.User;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoggerFactory.class})
public class EmailSenderImplTest {

    private static Logger mockLOG;

    @InjectMocks
    private EmailSenderImpl emailSender;

    @BeforeClass
    public static void setup() {
        mockStatic(LoggerFactory.class);
        mockLOG = mock(Logger.class);
        when(LoggerFactory.getLogger(any(Class.class))).thenReturn(mockLOG);
    }

    @Test
    public void shouldCallLogger() throws Exception {
        //Given
        User user = User.builder().email("jon@xxx.com").firstName("Jon").lastName("Bernard").build();

        //When
        emailSender.sendEmail(user);

        //Then
        verify(mockLOG).info("Email was successfully sent to the recipient with email: {}, first name: {}, last name: {}", user.getEmail(), user.getFirstName(), user.getLastName());
    }
}