package ru.jbimer.core.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.jbimer.core.security.EngineerDetails;
import ru.jbimer.core.util.EngineerValidator;
import ru.jbimer.core.models.Engineer;
import ru.jbimer.core.services.EngineersService;

import java.util.*;

@Controller
@RequestMapping("/engineers")
public class EngineersController {

    private final EngineerValidator engineerValidator;
    private final EngineersService engineersService;

    @Autowired
    public EngineersController(EngineerValidator engineerValidator, EngineersService engineersService) {
        this.engineerValidator = engineerValidator;
        this.engineersService = engineersService;
    }

    @GetMapping()
    public String index(Model model) {
        List<Engineer> engineers = engineersService.findAllSortedByCollisionsSize();

        model.addAttribute("engineers", engineers);
        return "engineers/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Engineer engineer = engineersService.findByIdFetchCollisions(id);

        model.addAttribute("engineer", engineer);
        model.addAttribute("collisions", engineer.getCollisions());
        return "engineers/show";
    }


    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {

        model.addAttribute("engineer", engineersService.findOne(id));
        return "engineers/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("engineer") @Valid Engineer updatedEngineer,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {

        if (bindingResult.hasErrors()) return "engineers/edit";

        Engineer originalEngineer = engineersService.findOne(id);

        engineersService.update(id, updatedEngineer, originalEngineer);
        return "redirect:/engineers";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {

        engineersService.delete(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/logout";

    }



}
