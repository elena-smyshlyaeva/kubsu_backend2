package com.example.kubsu.controller;

import com.example.kubsu.dto.UserDto;
import com.example.kubsu.model.Role;
import com.example.kubsu.model.User;
import com.example.kubsu.service.UserService;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@RequestMapping("auth")
public class UserController {

    private final UserService userService;

    @GetMapping("order")
    public String getOrder(Model model, Authentication authentication) {
        UserDto principal = (UserDto) authentication.getPrincipal();
        User user = userService.getUserByLogin(principal.getLogin());
        model.addAttribute("form", user);
        return "order";
    }

    @GetMapping("main")
    public String main(Model model, Authentication authentication) {
        UserDto userDto = (UserDto) authentication.getPrincipal();
        model.addAttribute("name", userDto.getName());
        return "mainpage";
    }

    @PostMapping("success")
    public String update(@Valid @ModelAttribute("form") User form,
        BindingResult bindingResult,
        Model model,
        Authentication authentication) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.forEach(System.out::println);
            return "form";
        }
        model.addAttribute("form", form);
        UserDto userDto = (UserDto) authentication.getPrincipal();
        userService.updateUser(form, userDto.getLogin());
        return "success";
    }
}
