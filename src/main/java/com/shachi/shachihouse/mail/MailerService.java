
package com.shachi.shachihouse.mail;


import jakarta.mail.MessagingException;

public interface MailerService {
    void send(MailModel mail) throws MessagingException;
    void send(String to, String subject, String body) throws MessagingException;
    void queue(MailModel mail);
    void queue(String to, String subject, String body);
}