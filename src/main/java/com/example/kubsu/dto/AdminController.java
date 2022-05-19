package com.example.kubsu.dto;

import com.example.kubsu.model.Role;
import com.example.kubsu.model.SuperPower;
import com.example.kubsu.model.User;
import com.example.kubsu.repository.UserRepository;
import com.example.kubsu.service.UserService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping
    public String adminPage(@AuthenticationPrincipal UserDto userDto, Model model) {
        List<User> orders = userService.getAllOrders();
        if (userDto.getRole().equals(Role.ADMIN)) {
            model.addAttribute("orders", orders);
            return "admin/admin";
        }
        return "redirect:/auth/main";
    }

    @GetMapping("order/{id}")
    public String editOrder(@AuthenticationPrincipal UserDto userDto, @PathVariable Long id, Model model) {
        if (!userDto.getRole().equals(Role.ADMIN)) {
            return "redirect:/auth/main";
        }
        User user = userService.getUserById(id);
        model.addAttribute("form", user);
        model.addAttribute("id", id);
        return "admin/admin-order";
    }

    @PostMapping("order/{id}")
    public String deleteOrder(@AuthenticationPrincipal UserDto userDto, @PathVariable Long id, Model model) {
        if (!userDto.getRole().equals(Role.ADMIN)) {
            return "redirect:/auth/main";
        }
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("statistic")
    public String getSuperPowersStatistic(@AuthenticationPrincipal UserDto userDto, Model model) {
        if (!userDto.getRole().equals(Role.ADMIN)) {
            return "redirect:/auth/main";
        }
        Integer animalSpeaking = userRepository.countUsersBySuperPower(
            SuperPower.ANIMAL_SPEAKING.getDisplayValue());
        model.addAttribute("animal", animalSpeaking);
        Integer flight = userRepository.countUsersBySuperPower(
            SuperPower.FLIGHT.getDisplayValue());
        model.addAttribute("flight", flight);
        Integer thoughtReading = userRepository.countUsersBySuperPower(
            SuperPower.THOUGHT_READING.getDisplayValue());
        model.addAttribute("thought", thoughtReading);
        Integer timeManagement = userRepository.countUsersBySuperPower(
            SuperPower.TIME_MANAGEMENT.getDisplayValue());
        model.addAttribute("time", timeManagement);
        return "admin/statistic";
    }

    @PostMapping("order/{id}/update")
    public String updateUserForm(@AuthenticationPrincipal UserDto userDto,
        @Valid @ModelAttribute("form") User form,
        @PathVariable Long id) {
        if (!userDto.getRole().equals(Role.ADMIN)) {
            return "redirect:/auth/main";
        }
        userService.updateById(form, id);
        return "admin/admin-success";
    }
}
