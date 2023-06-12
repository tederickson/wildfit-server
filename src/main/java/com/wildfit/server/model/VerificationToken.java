package com.wildfit.server.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "VERIFICATION_TOKEN", indexes = {@Index(name = "token_idx1", columnList = "token")})
public class VerificationToken {
    private static final int EXPIRATION = 60 * 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Date expiryDate;

    private Date calculateExpirationDate() {
        return new Date(Instant.now().plusSeconds(EXPIRATION).toEpochMilli());
    }

    public VerificationToken() {
        expiryDate = calculateExpirationDate();
    }

    public VerificationToken(String token, User user) {
        this();
        this.token = token;
        this.user = user;
    }
}
