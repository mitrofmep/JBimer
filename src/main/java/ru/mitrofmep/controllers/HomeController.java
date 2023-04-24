package ru.mitrofmep.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mitrofmep.services.CollisionsService;
import ru.mitrofmep.services.EngineersService;

@Controller
public class HomeController {

    private final EngineersService engineersService;
    private final CollisionsService collisionsService;

    public HomeController(EngineersService engineersService, CollisionsService collisionsService) {
        this.engineersService = engineersService;
        this.collisionsService = collisionsService;
    }

    @RequestMapping("/main")
    public String index(Model model) {
        model.addAttribute("engineers", engineersService.findAll());
        model.addAttribute("collisions", collisionsService.findAll());
        return "index_main";
    }
}
