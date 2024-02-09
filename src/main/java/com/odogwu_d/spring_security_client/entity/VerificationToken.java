package com.odogwu_d.spring_security_client.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor

public class VerificationToken {

    private static final int EXPIRATION_TIME = 10;
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String token;
    private Date expirationTime;

    @OneToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "user_Id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_USER_VERIFICATION_TOKEN"))

    private User user;


    public VerificationToken(User user, String token) {
        super();
        this.token = token;
        this.user = user;
        this.expirationTime = CalculateExpirationTime(EXPIRATION_TIME);
    }

    public VerificationToken(String token) {
        super();
        this.token = token;
        this.expirationTime = CalculateExpirationTime(EXPIRATION_TIME);


    }


    private Date CalculateExpirationTime(int expirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(calendar.MINUTE, expirationTime);

        return new Date(calendar.getTime().getTime());
    }


}
