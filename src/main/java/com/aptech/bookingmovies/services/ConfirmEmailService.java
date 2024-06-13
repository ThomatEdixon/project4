package com.aptech.bookingmovies.services;

import com.aptech.bookingmovies.models.ConfirmEmail;
import com.aptech.bookingmovies.models.User;
import com.aptech.bookingmovies.repositories.ConfirmEmailRepository;
import com.aptech.bookingmovies.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ConfirmEmailService implements IConfirmEmailService{
    private final ConfirmEmailRepository confirmEmailRepository;
    @Autowired
    private final JavaMailSender javaMailSender;

    @Override
    public ConfirmEmail createConfirmEmail(User user) {
        LocalDateTime expiredTime = LocalDateTime.now().plusMinutes(1);
        ConfirmEmail confirmEmail = ConfirmEmail.builder()
                .confirmCode(generateOTP())
                .expriedTime(expiredTime)
                .isConfirm(true)
                .user(user)
                .build();
        return confirmEmailRepository.save(confirmEmail);
    }

    @Override
    public boolean checkingConfirmCode(String email,String confirmCode) {
        if(email!= null&& confirmCode!=null){
            ConfirmEmail confirmEmail = confirmEmailRepository.findByConfirmCode(confirmCode);
            if(confirmEmail!= null ){
                if( LocalDateTime.now().isBefore(confirmEmail.getExpriedTime())){
                    return true;
                }else {
                    confirmEmail.setConfirm(false);
                }
            }
        }
        return false;
    }
    @Override
    public String sendEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp);
        message.setFrom("son.lv.2044@aptechlearning.edu.vn");
        javaMailSender.send(message);
        return "send email success";
    }
    public String generateOTP(){
        String otp = "";
        String characters = "0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        otp +=sb.toString();
        return otp;
    }
}
