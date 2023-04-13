package ru.mitrofmep.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mitrofmep.services.CollisionService;
import ru.mitrofmep.services.EngineerService;

@Controller
public class HomeController {

    private final EngineerService engineerService;
    private final CollisionService collisionService;

    public HomeController(EngineerService engineerService, CollisionService collisionService) {
        this.engineerService = engineerService;
        this.collisionService = collisionService;
    }

    @RequestMapping("/main")
    public String index(Model model) {
        model.addAttribute("engineers", engineerService.findAll());
        model.addAttribute("collisions", collisionService.findAll());
        return "index_main";
    }
}
