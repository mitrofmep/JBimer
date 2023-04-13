package ru.mitrofmep.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mitrofmep.dao.CollisionDAO;
import ru.mitrofmep.dao.EngineerDAO;
import ru.mitrofmep.models.Collision;
import ru.mitrofmep.models.Engineer;
import ru.mitrofmep.services.CollisionService;
import ru.mitrofmep.services.EngineerService;

import javax.validation.Valid;

@Controller
@RequestMapping("/collisions")
public class CollisionController {

    private final CollisionDAO collisionDAO;
    private final EngineerDAO engineerDAO;
    private final EngineerService engineerService;
    private final CollisionService collisionService;

    @Autowired
    public CollisionController(CollisionDAO collisionDAO, EngineerDAO engineerDAO, EngineerService engineerService, CollisionService collisionService) {
        this.collisionDAO = collisionDAO;
        this.engineerDAO = engineerDAO;
        this.engineerService = engineerService;
        this.collisionService = collisionService;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "collisions_per_page", required = false) Integer collisionsPerPage) {
        if (page == null || collisionsPerPage == null)
            model.addAttribute("collisions", collisionService.findAll());
        else
            model.addAttribute("collisions", collisionService.findWithPagination(page, collisionsPerPage));

        model.addAttribute("engineers", engineerService.findAll());
        return "collisions/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model,
                       @ModelAttribute("engineer") Engineer engineer) {
        model.addAttribute("collision", collisionService.findOne(id));

        Engineer collisionOwner = collisionService.getCollisionEngineer(id);

        if (collisionOwner != null)
            model.addAttribute("owner", collisionOwner);
        else
            model.addAttribute("engineers", engineerService.findAll());

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
        collisionService.save(collision);
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

        collisionService.delete(id);
        return "redirect:/collisions";

    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        collisionService.release(id);
        return "redirect:/collisions/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("engineer")Engineer engineer) {
        collisionService.assign(id, engineer);
        return "redirect:/collisions/" + id;
    }

    @GetMapping("/search")
    public String searchPage() {
        return "collisions/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("collisions", collisionService.searchByDiscipline(query));
        return "collisions/search";
    }
}
