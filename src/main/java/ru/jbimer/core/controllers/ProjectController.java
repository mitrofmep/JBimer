package ru.jbimer.core.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.jbimer.core.models.Engineer;
import ru.jbimer.core.models.Project;
import ru.jbimer.core.services.EngineersService;
import ru.jbimer.core.services.ProjectService;

import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final EngineersService engineersService;

    @Autowired
    public ProjectController(ProjectService projectService, EngineersService engineersService) {
        this.projectService = projectService;
        this.engineersService = engineersService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("projects", projectService.findAll());
        return "projects/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Project project = projectService.findOne(id);
        List<Engineer> engineers = project.getEngineersOnProject();
        model.addAttribute("project", project);
        model.addAttribute("engineers", engineers);

        return "projects/show";
    }

    @GetMapping("/new")
    public String newProject(Model model,
                             @ModelAttribute("project") Project project) {
        model.addAttribute("engineers", engineersService.findAll());

        return "projects/new";
    }

    @PostMapping
    public String create(@ModelAttribute("project") @Valid Project project,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "projects/new";
        projectService.save(project);
        return "redirect:/projects";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("project", projectService.findOne(id));
        model.addAttribute("engineers", engineersService.findAll());
        return "projects/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("project") @Valid Project project,
                         BindingResult bindingResult,
                       @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "projects/edit";
        projectService.save(project);
        return "redirect:/projects/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        projectService.delete(id);
        return "redirect:/projects";
    }


}
