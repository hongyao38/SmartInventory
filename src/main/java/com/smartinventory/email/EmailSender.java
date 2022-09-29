package com.smartinventory.email;

public interface EmailSender {

    void send(String to, String emailBody, String subject);
    
}
