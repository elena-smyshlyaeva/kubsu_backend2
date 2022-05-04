package com.example.kubsu.controller;

import com.example.kubsu.dto.CredentialsDto;
import com.example.kubsu.dto.UserDto;
import com.example.kubsu.model.User;
import com.example.kubsu.repository.UserRepository;
import com.example.kubsu.service.impls.AuthenticationService;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @GetMapping("/login")
    public String tryLogin(Model model, CredentialsDto user) {
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping("/auth")
    public String login(@ModelAttribute("user") CredentialsDto user, HttpServletResponse response, Model model) {
        UserDto userDto = getUserDto(user);
        auth(userDto);

        model.addAttribute(user);
        log.info("user with login=" + user.getLogin() + " has been authenticated");
        //Cookie cookie = new Cookie(CookieAuthenticationFilter.COOKIE_NAME, authenticationService.createToken(userDto));
        Cookie cookie = new Cookie("test", "test");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(60*30);
        cookie.setPath("/");

        response.addCookie(cookie);
        log.info("authentication cookie has been set");

        return "redirect:/main";
    }

    private UserDto getUserDto(CredentialsDto credentialsDto) {
        UserDto userDto = new UserDto();
        userDto.setLogin(credentialsDto.getLogin());

        User foundUser = userRepository.findByLogin(credentialsDto.getLogin()).orElseThrow(
            NoSuchElementException::new);
        userDto.setId(foundUser.getId());

        return userDto;
    }

    public void auth(@AuthenticationPrincipal UserDto dto) {
    }
}
