package com.ncit.teko.functionality;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.ncit.teko.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(User user) throws MessagingException, UnsupportedEncodingException {

        String siteURL = "http://localhost:8080";

        String toAddress = user.getEmail();
        String fromAddress = "tekomultinational@gmail.com";
        String senderName = "Ibritz tech";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Ibritz tech";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstname()+" "+user.getLastname());
        String verifyURL = siteURL+"/signup/verify?code="+user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);


    }

    public void changeEmailAddress(User user, String email) throws MessagingException, UnsupportedEncodingException{
        String siteURL = "http://localhost:8080";

        String toAddress = email;
        String fromAddress = "tekomultinational@gmail.com";
        String senderName = "Ibritz tech";
        String subject = "Please confirm your new email";
        String content = "Dear [[name]],<br>"
                + "Your verification code is:<br>"
                +"<h2>[[code]]</h2>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Ibritz tech";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstname()+" "+user.getLastname());
        String verifyURL = siteURL+"/profile/change-email?id="+user.getUserId()+"&email="+user.getEmail();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }
    
}
