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
    public String adminPage(Model model) {
        List<User> orders = userService.getAllOrders();
        model.addAttribute("orders", orders);
        return "admin/admin";

    }

    @GetMapping("order/{id}")
    public String editOrder(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("form", user);
        model.addAttribute("id", id);
        return "admin/admin-order";
    }

    @PostMapping("order/{id}")
    public String deleteOrder(@PathVariable Long id, Model model) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("statistic")
    public String getSuperPowersStatistic(Model model) {
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
    public String updateUserForm(
        @Valid @ModelAttribute("form") User form,
        @PathVariable Long id) {

        userService.updateById(form, id);
        return "admin/admin-success";
    }
}
