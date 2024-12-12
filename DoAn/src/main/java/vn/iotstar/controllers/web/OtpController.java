package vn.iotstar.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.iotstar.services.implement.OtpService;

@Controller
public class OtpController {

    @Autowired
    private OtpService otpService;

    // Hiển thị form nhập email
    @GetMapping("/request-otp")
    public String requestOtpForm() {
        return "requestOtp";
    }

    // Xử lý gửi OTP
    @PostMapping("/request-otp")
    public String sendOtp(@RequestParam String email, Model model) {
        String otp = otpService.generateOtp(email);
        otpService.sendOtp(email, otp);
        model.addAttribute("email", email);
        return "verifyOtp";
    }

    // Xác minh OTP
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email, @RequestParam String otp, Model model) {
        if (otpService.validateOtp(email, otp)) {
            model.addAttribute("message", "OTP verified successfully!");
            return "home";
        } else {
            model.addAttribute("message", "Invalid OTP. Please try again.");
            return "verifyOtp";
        }
    }
}
