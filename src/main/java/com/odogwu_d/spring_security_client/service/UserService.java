package com.odogwu_d.spring_security_client.service;

import com.odogwu_d.spring_security_client.entity.User;
import com.odogwu_d.spring_security_client.entity.VerificationToken;
import com.odogwu_d.spring_security_client.model.UserModel;

public interface UserService {
    User registerUser(UserModel userModel);



    void saveVerificationTokenForUser(String token, User user);

    String validateVerifyToken(String token);

    VerificationToken generateNewVerification(String oldToken);
}
