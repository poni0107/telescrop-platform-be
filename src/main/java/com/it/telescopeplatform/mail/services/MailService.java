package com.it.telescopeplatform.mail.services;

public interface MailService {
    void sendEmail(String to, String subject, String body);
}