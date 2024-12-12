package vn.iotstar.services.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private JavaMailSender mailSender;

    private Map<String, String> otpStorage = new HashMap<>();

    // Tạo OTP ngẫu nhiên
    public String generateOtp(String email) {
        String otp = String.format("%06d", new Random().nextInt(999999)); // OTP gồm 6 chữ số
        otpStorage.put(email, otp);
        return otp;
    }

    // Gửi OTP qua email
    public void sendOtp(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP is: " + otp + ". It will expire in 5 minutes.");
        mailSender.send(message);
    }

    // Xác minh OTP
    public boolean validateOtp(String email, String otp) {
        return otpStorage.containsKey(email) && otpStorage.get(email).equals(otp);
    }
}
