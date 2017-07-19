package com.ninyo.send.emails.service;

import com.ninyo.send.emails.model.User;
import com.ninyo.send.emails.worker.EmailSender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceImplTest {

    @Mock
    private EmailSender emailSender;

    @InjectMocks
    private EmailServiceImpl service;

    @Before
    public void init() {
        ReflectionTestUtils.setField(service, "filePath", "src/test/resources/Users.csv");
    }

    @Test
    public void shouldCallEmailSenderTenThousandTimes() throws IOException {
        shouldCallEmailSender(10000);
    }

    @Test
    public void shouldCallEmailSenderHundredThousandTimes() throws IOException {
        shouldCallEmailSender(100000);
    }

    private void shouldCallEmailSender(int times) throws IOException {
        //When
        new CsvGenerator(times).generate();
        service.sendEmails();

        //Then
        verify(emailSender, times(times)).sendEmail(any(User.class));
    }
}