package com.ncit.teko.service;

import com.ncit.teko.model.User;
import com.ncit.teko.repository.UserRepo;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JavaMailSender mailSender;

    public void userSignup(User user){
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        userRepo.save(user);

        try {
            sendVerificationEmail(user);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public boolean doesUserAlreadyExists(String username,String email){
        return userRepo.existsUserByUsernameOrEmail(username,email);
    }

    public boolean isUserNameTaken(String username){
        return userRepo.existsUserByUsername(username);
    }

    public boolean isEmailTaken(String email){
        return userRepo.existsUserByEmail(email);
    }

    public User loginUser(String email,String password){

        return userRepo.checkUser(email,password);
    }

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
                + "Your company name.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstname()+" "+user.getLastname());
        String verifyURL = siteURL+"/verify?code="+user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);


    }

    public boolean verify(String code){
        User user = userRepo.checkByVerificationCode(code);
        if(user == null || user.isEnabled()){
            return false;
        }else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepo.save(user);
            return true;
        }
    }

}
