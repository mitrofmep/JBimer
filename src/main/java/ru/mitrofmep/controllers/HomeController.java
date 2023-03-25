package ru.mitrofmep.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mitrofmep.dao.CollisionDAO;
import ru.mitrofmep.dao.EngineerDAO;

@Controller
public class HomeController {

    private final EngineerDAO engineerDAO;
    private final CollisionDAO collisionDAO;

    @Autowired
    public HomeController(EngineerDAO engineerDAO, CollisionDAO collisionDAO) {
        this.engineerDAO = engineerDAO;
        this.collisionDAO = collisionDAO;
    }

    @RequestMapping("/main")
    public String index(Model model) {
        model.addAttribute("engineers", engineerDAO.index());
        model.addAttribute("collisions", collisionDAO.index());
        return "index_main";
    }
}
