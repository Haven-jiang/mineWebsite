package com.Haven.service.impl;

import com.Haven.DTO.EmailInfoDTO;
import com.Haven.service.EmailSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.File;
import java.util.Date;
import java.util.Objects;

@Service
public class EmailSendServiceImpl implements EmailSendService {

    @Value("${spring.mail.username}")
    private String email;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendMimeMail(EmailInfoDTO emailInfo) {

        try {
            MimeMessageHelper simpleMailMessage = new MimeMessageHelper(mailSender.createMimeMessage(), true);

            simpleMailMessage.setFrom(email);
            simpleMailMessage.setTo(emailInfo.getRecipient().split(","));

            simpleMailMessage.setSubject(emailInfo.getSubject());
            simpleMailMessage.setText(emailInfo.getText(), true);
            if (!Objects.isNull(emailInfo.getCc()))
                simpleMailMessage.setCc(emailInfo.getCc().split(","));

            if (!Objects.isNull(emailInfo.getBcc()))
                simpleMailMessage.setCc(emailInfo.getBcc().split(","));

            if (emailInfo.getMultipartFiles() != null)
                for (MultipartFile multipartFile : emailInfo.getMultipartFiles()) {
                    simpleMailMessage.addAttachment(Objects.requireNonNull(multipartFile.getOriginalFilename()), multipartFile);
                }

            if (Objects.isNull(emailInfo.getSentDate()))
                emailInfo.setSentDate(new Date());

            simpleMailMessage.setSentDate(emailInfo.getSentDate());

            String alarmIconName = "success-alarm.png";
            ClassPathResource img = new ClassPathResource(alarmIconName);
            simpleMailMessage.addInline("icon", img);

            for (EmailInfoDTO.Inline inline : emailInfo.getInlines()) {
                if (inline.getContentFile().exists())
                    simpleMailMessage.addInline(inline.getContentId(), inline.getContentFile());
            }
//            simpleMailMessage.addAttachment("image.jpg", new ClassPathResource("image.jpg"));

            mailSender.send(simpleMailMessage.getMimeMessage());
            emailInfo.setStatus("ok");
            logger.info("发送邮件成功：{}->{}", email, emailInfo.getRecipient());

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}
