package ru.jbimer.core.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.jbimer.core.models.Engineer;
import ru.jbimer.core.security.EngineerDetails;
import ru.jbimer.core.services.CollisionsService;
import ru.jbimer.core.services.EngineersService;
import ru.jbimer.core.services.ProjectService;

@Controller
public class HomeController {

    private final EngineersService engineersService;
    private final CollisionsService collisionsService;
    private final ProjectService projectService;

    public HomeController(EngineersService engineersService, CollisionsService collisionsService, ProjectService projectService) {
        this.engineersService = engineersService;
        this.collisionsService = collisionsService;
        this.projectService = projectService;
    }

    @RequestMapping("/main")
    public String index(Model model) {
        model.addAttribute("engineers", engineersService.findAll());
        model.addAttribute("collisions", collisionsService.findAll());
        model.addAttribute("projects", projectService.findAll());
        return "index_main";
    }

    @GetMapping("/account")
    public String showUserAccount(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EngineerDetails engineerDetails = (EngineerDetails) authentication.getPrincipal();

        Engineer engineer = engineersService
                .findByIdFetchCollisions(engineerDetails.getEngineer().getId());

        model.addAttribute("engineer", engineer);
        model.addAttribute("collisions", engineer.getCollisions());

        return "/profile_page";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }
}
