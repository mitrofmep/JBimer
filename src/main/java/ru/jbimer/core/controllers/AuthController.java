package ru.jbimer.core.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.jbimer.core.models.Engineer;
import ru.jbimer.core.services.EngineersService;
import ru.jbimer.core.util.EngineerValidator;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final EngineerValidator engineerValidator;
    private final EngineersService engineersService;

    public AuthController(EngineerValidator engineerValidator, EngineersService engineersService) {
        this.engineerValidator = engineerValidator;
        this.engineersService = engineersService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("engineer") Engineer engineer) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("engineer") @Valid Engineer engineer,
                                      BindingResult bindingResult) {
        engineerValidator.validate(engineer, bindingResult);

        if (bindingResult.hasErrors()) return "auth/registration";
        engineersService.save(engineer);

        return "redirect:/auth/login";
    }
}
