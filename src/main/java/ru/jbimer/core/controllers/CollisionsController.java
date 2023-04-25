package ru.jbimer.core.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.jbimer.core.models.Collision;
import ru.jbimer.core.models.Engineer;
import ru.jbimer.core.services.CollisionsService;
import ru.jbimer.core.services.EngineersService;


@Controller
@RequestMapping("/collisions")
public class CollisionsController {

    private final EngineersService engineersService;
    private final CollisionsService collisionsService;

    @Autowired
    public CollisionsController(EngineersService engineersService, CollisionsService collisionsService) {
        this.engineersService = engineersService;
        this.collisionsService = collisionsService;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "collisions_per_page", required = false) Integer collisionsPerPage) {
        if (page == null || collisionsPerPage == null)
            model.addAttribute("collisions", collisionsService.findAll());
        else
            model.addAttribute("collisions", collisionsService.findWithPagination(page, collisionsPerPage));

//        model.addAttribute("engineers", engineerService.findAll());
        return "collisions/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model,
                       @ModelAttribute("engineer") Engineer engineer) {
        Collision collision = collisionsService.findOneAndEngineer(id);

        Engineer collisionOwner = collision.getEngineer();

        model.addAttribute("collision", collision);
        if (collisionOwner != null){
            model.addAttribute("owner", collisionOwner);
        }
        else
            model.addAttribute("engineers", engineersService.findAll());

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
        collisionsService.save(collision);
        return "redirect:/collisions";
    }

//    @GetMapping("/{id}/edit")
//    public String edit(Model model, @PathVariable("id") int id) {
//
//        model.addAttribute("collision", collisionService.findOne(id));
//        return "collisions/edit";
//    }

//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("collision") @Valid Collision collision,
//                         BindingResult bindingResult,
//                         @PathVariable("id") int id) {
//
//
//        if (bindingResult.hasErrors()) return "collisions/edit";
//        collisionService.update(id, collision);
//        return String.format("redirect:/collisions/%s", id);
//    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {

        collisionsService.delete(id);
        return "redirect:/collisions";

    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        collisionsService.release(id);
        return "redirect:/collisions/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("engineer")Engineer engineer) {
        collisionsService.assign(id, engineer);
        return "redirect:/collisions/" + id;
    }


    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("collisions", collisionsService.searchByDiscipline(query));
        return "collisions/index";
    }

    @GetMapping("/{id}/fake")
    public String markAsFake(@PathVariable("id") int id) {
        collisionsService.markAsFake(id);
        return "redirect:/collisions";
    }
}
