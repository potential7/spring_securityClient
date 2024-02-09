package com.odogwu_d.spring_security_client.contoller;

import com.odogwu_d.spring_security_client.entity.User;
import com.odogwu_d.spring_security_client.entity.VerificationToken;
import com.odogwu_d.spring_security_client.event.RegistrationCompleteEvent;
import com.odogwu_d.spring_security_client.model.UserModel;
import com.odogwu_d.spring_security_client.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest request) {
        User user = userService.registerUser(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return " save Completed";
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token){
        String result = userService.validateVerifyToken(token);
        if(result.equalsIgnoreCase("valid"))
            return "User verified Successfully";
        else {
            return "inValid user";
        }
    }
  @GetMapping("/resendVerificationToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request){
        VerificationToken verificationToken =
                userService.generateNewVerification(oldToken);
        User user = verificationToken.getUser();
        resendVerificationMail(user, applicationUrl(request), verificationToken);
        return "Verification Link As been sent";
    }

    private void resendVerificationMail(User user, String applicationUrl, VerificationToken verificationToken) {

        String url = applicationUrl + "/verifyRegistration?token="
                + verificationToken.getToken();

        log.info("click the link to verify your account: {}" , url);

    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}
