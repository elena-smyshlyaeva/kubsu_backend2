package com.example.kubsu.service.impls;

import com.example.kubsu.dto.CredentialsDto;
import com.example.kubsu.dto.UserDto;
import com.example.kubsu.model.User;
import com.example.kubsu.repository.UserRepository;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.Objects;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    private final UserRepository userRepository;
    private String secretKey = "secret";


    public UserDto authenticate(CredentialsDto credentialsDto) {
        String login = credentialsDto.getLogin();
        char[] password = credentialsDto.getPassword();
        User foundUser = userRepository.findByLogin(login).orElseThrow(NoSuchElementException::new);

        if (passwordEncoder.matches(CharBuffer.wrap(password), foundUser.getPassword())){
            log.info("password are equals!");
            return new UserDto(foundUser.getId(), foundUser.getName(), foundUser.getLogin(), foundUser.getRole());
        }
        throw new RuntimeException("Invalid password");
    }

    public UserDto findByLogin(String login) {
        User foundUser = userRepository.findByLogin(login).orElseThrow(NoSuchElementException::new);

        if (foundUser.getLogin().equals(login)) {
            log.info("logins are equals!");
            return new UserDto(foundUser.getId(), foundUser.getName(), foundUser.getLogin(), foundUser.getRole());
        }
        throw new RuntimeException("Invalid login");
    }

    public String createToken(UserDto user) {
        String generatedToken = user.getId() + "&" + user.getLogin() + "&" + calculateHmac(user);
        log.info("toke: " + generatedToken);
        return generatedToken;
    }

    public UserDto findByToken(String token) {
        String[] parts = token.split("&");

        Long userId = Long.valueOf(parts[0]);
        String login = parts[1];

        UserDto userDto = findByLogin(login);

        if (!userId.equals(userDto.getId())) {
            throw new RuntimeException("Invalid Cookie value");
        }

        return userDto;
    }


    private String calculateHmac(UserDto user) {
        byte[] secretKeyBytes = Objects.requireNonNull(secretKey).getBytes(StandardCharsets.UTF_8);
        byte[] valueBytes = Objects.requireNonNull(user.getId() + "&" + user.getLogin()).getBytes(StandardCharsets.UTF_8);

        try {
            Mac mac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "HmacSHA512");
            mac.init(secretKeySpec);
            byte[] hmacBytes = mac.doFinal(valueBytes);
            return Base64.getEncoder().encodeToString(hmacBytes);

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
