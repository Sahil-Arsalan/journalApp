package com.alamara.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    void testSendEmail()
    {
        emailService.sendEmail("arsalanalam186@gmail.com",
                "Testing Java Email Sender",
                "To kaise Hai app log ?");
    }
}
