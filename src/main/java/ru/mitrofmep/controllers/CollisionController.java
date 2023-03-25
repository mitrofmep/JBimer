package ru.mitrofmep.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mitrofmep.dao.CollisionDAO;
import ru.mitrofmep.dao.EngineerDAO;
import ru.mitrofmep.models.Collision;

import javax.validation.Valid;

@Controller
@RequestMapping("/collisions")
public class CollisionController {

    private final CollisionDAO collisionDAO;
    private final EngineerDAO engineerDAO;

    @Autowired
    public CollisionController(CollisionDAO collisionDAO, EngineerDAO engineerDAO) {
        this.collisionDAO = collisionDAO;
        this.engineerDAO = engineerDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("collisions", collisionDAO.index());
        return "collisions/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("collision", collisionDAO.show(id));
        model.addAttribute("engineers", engineerDAO.index());
        return "collisions/show";
    }

    @GetMapping("/new")
    public String newCollision(@ModelAttribute("collision") Collision collision) {
        return "collisions/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("collision") @Valid Collision collision,
                         BindingResult bindingResult) {


        if (bindingResult.hasErrors()) return "collisions/new";
        collisionDAO.save(collision);
        return "redirect:/collisions";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {

        model.addAttribute("collision", collisionDAO.show(id));
        return "collisions/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("collision") @Valid Collision collision,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {


        if (bindingResult.hasErrors()) return "collisions/edit";
        collisionDAO.update(id, collision);
        return "redirect:/collisions";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {

        collisionDAO.delete(id);
        return "redirect:/collisions";

    }

    @PatchMapping("/set{id}")
    public String setEngineer(@ModelAttribute("collision") Collision collision,
                              @PathVariable("id") int id) {

        System.out.println(id);
        System.out.println(collision.getEngineer_id());
        collisionDAO.set(id, collision.getEngineer_id());
        return "redirect:/collisions";
    }
}
