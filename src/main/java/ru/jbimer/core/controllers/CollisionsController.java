package ru.jbimer.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.jbimer.core.models.Collision;
import ru.jbimer.core.models.Engineer;
import ru.jbimer.core.models.Project;
import ru.jbimer.core.security.EngineerDetails;
import ru.jbimer.core.services.CollisionsService;
import ru.jbimer.core.services.EngineersService;
import ru.jbimer.core.services.HtmlReportService;
import ru.jbimer.core.services.ProjectService;

import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/projects/{project_id}/collisions")
public class CollisionsController {

    private final EngineersService engineersService;
    private final CollisionsService collisionsService;
    private final HtmlReportService service;
    private final ProjectService projectService;

    @Autowired
    public CollisionsController(EngineersService engineersService, CollisionsService collisionsService, HtmlReportService service, ProjectService projectService) {
        this.engineersService = engineersService;
        this.collisionsService = collisionsService;
        this.service = service;
        this.projectService = projectService;
    }

    @GetMapping()
    public String index(Model model,
                        @PathVariable("project_id") int project_id,
                        @RequestParam(required = false) String keyword,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer size) {

        try {
            List<Collision> collisions;
            Pageable paging = PageRequest.of(page - 1, size, Sort.by("id"));

            Page<Collision> collisionPage;
            if (keyword == null) {
                collisionPage = collisionsService.findAllWithPagination(project_id, paging);
            } else {
                collisionPage = collisionsService.searchByDiscipline(keyword, project_id, paging);
                model.addAttribute("keyword", keyword);
            }

            collisions = collisionPage.getContent();

            Project project = projectService.findOne(project_id);

            model.addAttribute("collisions", collisions);
            model.addAttribute("currentPage", collisionPage.getNumber() + 1);
            model.addAttribute("totalItems", collisionPage.getTotalElements());
            model.addAttribute("totalPages", collisionPage.getTotalPages());
            model.addAttribute("pageSize", size);
            model.addAttribute("project", project);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "collisions/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       @PathVariable("project_id") int project_id,
                       Model model,
                       @ModelAttribute("engineer") Engineer engineer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authority = authentication.getAuthorities().stream().findFirst().orElse(null).getAuthority();


        Collision collision = collisionsService.findOneAndEngineer(id);

        Engineer collisionOwner = collision.getEngineer();

        model.addAttribute("collision", collision);
        model.addAttribute("comments", collision.getComments());
        model.addAttribute("role", authority);
        if (collisionOwner != null){
            model.addAttribute("owner", collisionOwner);
        }
        else
            model.addAttribute("engineers", engineersService.findAllOnProject(project_id));

        return "collisions/show";
    }

    @GetMapping("/upload")
    public String uploadCollisionsReportPage(Model model, @PathVariable("project_id") int project_id) {
        model.addAttribute("project", projectService.findOne(project_id));
        return "collisions/upload";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id, @PathVariable("project_id") int project_id) {

        collisionsService.delete(id);
        return "redirect:/projects/" + project_id + "/collisions";

    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id,
                          @PathVariable("project_id") int project_id) {
        collisionsService.release(id);
        return "redirect:/projects/" + project_id + "/collisions/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("engineer")Engineer engineer,
                         @PathVariable("project_id") int project_id) {
        collisionsService.assign(id, engineer);
        return "redirect:/projects/" + project_id + "/collisions/" + id;
    }

    @GetMapping("/{id}/fake")
    public String markAsFake(@PathVariable("id") int id, @PathVariable("project_id") int project_id) {
        collisionsService.markAsFake(id);
        return "redirect:/projects/" + project_id + "/collisions/" + id;
    }

    @GetMapping("/{id}/not-fake")
    public String markAsNotFake(@PathVariable("id") int id, @PathVariable("project_id") int project_id) {
        collisionsService.markAsNotFake(id);
        return "redirect:/projects/" + project_id + "/collisions/" + id;
    }

    @PostMapping("/{id}/add-comment")
    public String addComment(@PathVariable("id") int id,
                             @RequestParam("comment") String comment,
                             @PathVariable("project_id") int project_id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EngineerDetails engineerDetails = (EngineerDetails) authentication.getPrincipal();
        collisionsService.addComment(id, engineerDetails.getEngineer(), comment);

        return "redirect:/projects/" + project_id + "/collisions/" + id;
    }
}
