package com.example.kubsu.controller;

import com.example.kubsu.dto.CredentialsDto;
import com.example.kubsu.model.Role;
import com.example.kubsu.model.User;
import com.example.kubsu.service.UserService;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainPageController {

    private final UserService userService;
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    @GetMapping("/form")
    public String form(Model model, User form) {
        String name = getNamedCookies("name");
        if (name.isEmpty()) {
            model.addAttribute("form", form);
            return "form";
        }
        log.info("found name cookie " + name);
        form.setName(name);

        String email = getNamedCookies("email");
        if (email.isEmpty()) {
            model.addAttribute("form", form);
            return "form";
        }
        log.info("found email cookie " + email);
        form.setEmail(email);

        model.addAttribute("form", form);
        return "form";
    }

    private String getNamedCookies(String name) {
        Cookie[] cookies = request.getCookies();
        return Arrays.stream(cookies)
            .filter(cookie -> cookie.getName().equals(name))
            .map(Cookie::getValue)
            .findFirst().orElse("");
    }


    @PostMapping("/form")
    public String fillForm(@Valid @ModelAttribute("form") User form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            clearInvalidFormCookies();
            log.info("form is invalid!");
            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.forEach(e ->  setInvalidFormCookie(e.getField()));
            return "form";
        }
        model.addAttribute("form", form);

        clearInvalidFormCookies();
        setFormCookies(form);
        setLoginAndPassword(form);

        String rawPassword = form.getPassword();
        form.setRole(Role.USER);
        userService.saveUser(form);
        log.info("user successfully saved");

        form.setPassword(rawPassword);
        return "result";
    }

    private void setLoginAndPassword(User form) {
        String login = RandomString.make();
        String password = RandomString.make();
        form.setLogin(login);
        form.setPassword(password);
    }

    private void setFormCookies(User user) {
        Cookie nameCookie = new Cookie("name", user.getName());
        nameCookie.setMaxAge(60*60*24*365);
        Cookie emailCookie = new Cookie("email", user.getEmail());
        emailCookie.setMaxAge(60*60*24*365);

        response.addCookie(nameCookie);
        response.addCookie(emailCookie);
    }

    private void clearInvalidFormCookies() {
        Cookie nameCookie = new Cookie("name", "");
        nameCookie.setMaxAge(0);
        Cookie emailCookie = new Cookie("email", "");
        emailCookie.setMaxAge(0);

        response.addCookie(nameCookie);
        response.addCookie(emailCookie);
    }

    private void setInvalidFormCookie(String field) {
        Cookie cookie = new Cookie(field, "invalid");
        cookie.setMaxAge(60*5);
        response.addCookie(cookie);
        log.info("cookie " + cookie.getName() + "=" + cookie.getValue() + " has been set");
    }
}
