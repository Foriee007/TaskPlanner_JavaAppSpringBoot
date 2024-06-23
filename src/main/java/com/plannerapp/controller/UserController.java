package com.plannerapp.controller;

import com.plannerapp.config.UserSession;
import com.plannerapp.model.dto.UserLoginDto;
import com.plannerapp.model.dto.UserRegisterDto;
import com.plannerapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller public class UserController {
    private final UserService userService;
    private final UserSession userSession;

    public UserController(UserService userService, UserSession userSession) {
        this.userService = userService;
        this.userSession = userSession;
    }

    @ModelAttribute("userRegisterDto")
    public UserRegisterDto initUserRegisterDto() {
        return new UserRegisterDto();
    }

    @ModelAttribute("userLoginDto")
    public UserLoginDto initUserLoginDto() {
        return new UserLoginDto();
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userRegisterDto") UserRegisterDto userRegisterDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterDto", userRegisterDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDto", bindingResult);
            return "redirect:/register";
        }

        boolean isRegistered = userService.register(userRegisterDto);
        if (!isRegistered) {
            redirectAttributes.addFlashAttribute("userRegisterDto", userRegisterDto);
            redirectAttributes.addFlashAttribute("error", "Username or email already exists.");
            return "redirect:/register";
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@Valid @ModelAttribute("userLoginDto") UserLoginDto userLoginDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginDto", userLoginDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginDto", bindingResult);
            return "redirect:/login";
        }

        if (!userService.checkCredentials(userLoginDto.getUsername(), userLoginDto.getPassword())) {
            //redirectAttributes.addFlashAttribute("error", "Invalid username or password.");
            redirectAttributes.addFlashAttribute("validCredentials", false);
            redirectAttributes.addFlashAttribute("userLoginDto", userLoginDto);
            return "redirect:/login";
        }

        userService.login(userLoginDto.getUsername());
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logoutUser() {
        if (!this.userSession.isLoggedIn()) {
            return "redirect:/login";
        }
        userService.logout();
        return "redirect:/";
    }
}

