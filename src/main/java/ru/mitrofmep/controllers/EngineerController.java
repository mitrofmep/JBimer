package ru.mitrofmep.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mitrofmep.dao.CollisionDAO;
import ru.mitrofmep.dao.EngineerDAO;
import ru.mitrofmep.models.Engineer;
import ru.mitrofmep.services.CollisionService;
import ru.mitrofmep.services.EngineerService;
import ru.mitrofmep.util.EngineerValidator;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/engineers")
public class EngineerController {

    private final EngineerValidator engineerValidator;
    private final EngineerService engineerService;

    @Autowired
    public EngineerController(EngineerValidator engineerValidator,EngineerService engineerService) {
        this.engineerValidator = engineerValidator;
        this.engineerService = engineerService;
    }

    @GetMapping()
    public String index(Model model) {
        List<Engineer> engineers = engineerService.findAllSortedByCollisionsSize();

        model.addAttribute("engineers", engineers);
        return "engineers/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Engineer engineer = engineerService.findOneAndItsCollisions(id);

        model.addAttribute("engineer", engineer);
        model.addAttribute("collisions", engineer.getCollisions());
        return "engineers/show";
    }

    @GetMapping("/new")
    public String newEngineer(@ModelAttribute("engineer") Engineer engineer) {
        return "engineers/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("engineer") @Valid Engineer engineer,
                         BindingResult bindingResult) {

        engineerValidator.validate(engineer, bindingResult);

        if (bindingResult.hasErrors()) return "engineers/new";
        engineerService.save(engineer);
        return "redirect:/engineers";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {

        model.addAttribute("engineer", engineerService.findOne(id));
        return "engineers/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("engineer") @Valid Engineer engineer,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {

        engineerValidator.validate(engineer, bindingResult);


        if (bindingResult.hasErrors()) return "engineers/edit";
        engineerService.update(id, engineer);
        return "redirect:/engineers";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {

        engineerService.delete(id);
        return "redirect:/engineers";
        
    }
}
