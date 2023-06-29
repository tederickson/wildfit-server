package com.wildfit.server.service.handler;

import java.util.Objects;

import com.wildfit.server.domain.CreateUserResponse;
import com.wildfit.server.exception.UserServiceError;
import com.wildfit.server.exception.UserServiceException;
import com.wildfit.server.model.User;
import com.wildfit.server.model.UserProfile;
import com.wildfit.server.model.UserStatus;
import com.wildfit.server.model.VerificationToken;
import com.wildfit.server.model.mapper.CreateUserResponseMapper;
import com.wildfit.server.repository.UserProfileRepository;
import com.wildfit.server.repository.UserRepository;
import com.wildfit.server.repository.VerificationTokenRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Slf4j
@Builder(setterPrefix = "with")
public class CreateUserHandler {

    final UserRepository userRepository;
    final UserProfileRepository userProfileRepository;
    final VerificationTokenRepository verificationTokenRepository;
    final Environment environment;
    final JavaMailSender javaMailSender;
    final String email;
    final String password;
    final String name;

    public CreateUserResponse execute() throws UserServiceException {
        validate();

        if (!PasswordValidator.isValid(password)) {
            throw new UserServiceException(UserServiceError.INVALID_PASSWORD);
        }
        final var encodedPassword = PasswordEncodeDecode.encode(password);
        final var users = userRepository.findByEmail(email);

        if (!CollectionUtils.isEmpty(users)) {
            throw new UserServiceException(UserServiceError.EXISTING_USER);
        }

        final var user = User.builder()
                .withStatus(UserStatus.FREE.getCode())
                .withCreateDate(java.time.LocalDate.now())
                .withPassword(encodedPassword)
                .withEmail(email)
                .withEnabled(false)
                .build();
        final var userProfile = UserProfile.builder().withUser(user)
                .withName(name)
                .build();

        final var saved = userProfileRepository.save(userProfile);

        final var verificationToken = new VerificationToken(RandomStringUtils.randomAlphabetic(30),
                userProfile.getUser());

        verificationTokenRepository.save(verificationToken);
        sendEmail(verificationToken);

        return CreateUserResponseMapper.map(saved.getUser());
    }

    private void sendEmail(VerificationToken verificationToken) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(verificationToken.getUser().getEmail());

        msg.setSubject("WILDFIT account activiation");
        final var text = String.join("\n",
                "Dear " + name + ",",
                "",
                "Thank you for signing up for access to the WILDFIT application, your account has been created.",
                "",
                "To activate your account, please visit:",
                "http://localhost:8080/v1/auth/register/" + verificationToken.getToken(),
                "",
                "Your account will automatically be approved once you click this URL. " +
                        "If you have problems activating your account please contact support (support@wildfit.com).",
                "",
                "Warm Regards,",
                "Wildfit team"
        );
        msg.setText(text);

        javaMailSender.send(msg);
    }

    private void validate() throws UserServiceException {
        Objects.requireNonNull(userRepository, "userRepository");
        Objects.requireNonNull(userProfileRepository, "userProfileRepository");
        Objects.requireNonNull(verificationTokenRepository, "verificationTokenRepository");
        Objects.requireNonNull(javaMailSender, "javaMailSender");

        if (!StringUtils.hasText(email)) {
            throw new UserServiceException(UserServiceError.MISSING_EMAIL);
        }
        if (!StringUtils.hasText(password)) {
            throw new UserServiceException(UserServiceError.INVALID_PASSWORD);
        }
        if (!StringUtils.hasText(name)) {
            throw new UserServiceException(UserServiceError.INVALID_NAME);
        }

        if (environment == null || !environment.containsProperty("spring.mail.username")) {
            throw new UserServiceException(UserServiceError.EMAIL_NOT_CONFIGURED);
        }
    }
}

