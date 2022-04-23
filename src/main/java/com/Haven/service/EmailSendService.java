package com.Haven.service;

import com.Haven.DTO.EmailInfoDTO;

public interface EmailSendService {
    void sendMimeMail(EmailInfoDTO emailInfo);
}
