package com.odogwu_d.spring_security_client.event.listener;

import com.odogwu_d.spring_security_client.entity.User;
import com.odogwu_d.spring_security_client.event.RegistrationCompleteEvent;
import com.odogwu_d.spring_security_client.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
@Slf4j
public class RegistrationCompleteListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {

        User user = event.getUser();
        String token = UUID.randomUUID().toString();

        userService.saveVerificationTokenForUser(token, user);

        String url = event.getApplicationUrl() + "/verifyRegistration?token="
                + token;

        log.info("click the link to verify your account: {}" , url);
    }
}
