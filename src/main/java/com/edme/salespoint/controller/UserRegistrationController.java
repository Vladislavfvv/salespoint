package com.edme.salespoint.controller;

import com.edme.salespoint.dto.UserAccessDto;
import com.edme.salespoint.service.UserAccessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserRegistrationController {

    private final UserAccessService userAccessService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userAccessDto", new UserAccessDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute @Valid UserAccessDto userAccessDto, Model model) {
        userAccessDto.setUserPassword(passwordEncoder.encode(userAccessDto.getUserPassword()));
        var result = userAccessService.save(userAccessDto);
        if (result.isEmpty()) {
            model.addAttribute("error", "Пользователь с таким логином уже существует");
            return "register";
        }
        return "redirect:/login";
    }
}
