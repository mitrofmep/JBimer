package ru.jbimer.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.jbimer.core.exception.InvalidFileNameException;
import ru.jbimer.core.models.Collision;
import ru.jbimer.core.models.Engineer;
import ru.jbimer.core.security.EngineerDetails;
import ru.jbimer.core.services.CollisionsService;
import ru.jbimer.core.services.EngineersService;
import ru.jbimer.core.services.HtmlReportService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/collisions")
public class CollisionsController {

    private final EngineersService engineersService;
    private final CollisionsService collisionsService;
    private final HtmlReportService service;

    @Autowired
    public CollisionsController(EngineersService engineersService, CollisionsService collisionsService, HtmlReportService service) {
        this.engineersService = engineersService;
        this.collisionsService = collisionsService;
        this.service = service;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(required = false) String keyword,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer size) {

        try {
            List<Collision> collisions;
            Pageable paging = PageRequest.of(page - 1, size, Sort.by("id"));

            Page<Collision> collisionPage;
            if (keyword == null) {
                collisionPage = collisionsService.findAllWithPagination(paging);
            } else {
                collisionPage = collisionsService.searchByDiscipline(keyword, paging);
                model.addAttribute("keyword", keyword);
            }

            collisions = collisionPage.getContent();

            model.addAttribute("collisions", collisions);
            model.addAttribute("currentPage", collisionPage.getNumber() + 1);
            model.addAttribute("totalItems", collisionPage.getTotalElements());
            model.addAttribute("totalPages", collisionPage.getTotalPages());
            model.addAttribute("pageSize", size);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "collisions/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model,
                       @ModelAttribute("engineer") Engineer engineer) {
        Collision collision = collisionsService.findOneAndEngineer(id);

        Engineer collisionOwner = collision.getEngineer();

        model.addAttribute("collision", collision);
        model.addAttribute("comments", collision.getComments());
        if (collisionOwner != null){
            model.addAttribute("owner", collisionOwner);
        }
        else
            model.addAttribute("engineers", engineersService.findAll());

        return "collisions/show";
    }

    @GetMapping("/upload")
    public String uploadCollisionsReportPage(Model model) {
        return "collisions/upload";
    }

    @PostMapping()
    public String upload(@RequestParam("file") MultipartFile file,
                         Model model) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EngineerDetails engineerDetails = (EngineerDetails) authentication.getPrincipal();

        int collisions = service.uploadFile(file, engineerDetails.getEngineer());
        model.addAttribute("collisionsUploaded", collisions);

        return "collisions/upload";
    }

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

    @GetMapping("/{id}/fake")
    public String markAsFake(@PathVariable("id") int id) {
        collisionsService.markAsFake(id);
        return "redirect:/collisions/" + id;
    }

    @GetMapping("/{id}/not-fake")
    public String markAsNotFake(@PathVariable("id") int id) {
        collisionsService.markAsNotFake(id);
        return "redirect:/collisions/" + id;
    }

    @PostMapping("/{id}/add-comment")
    public String addComment(@PathVariable("id") int id,
                             @RequestParam("comment") String comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EngineerDetails engineerDetails = (EngineerDetails) authentication.getPrincipal();
        collisionsService.addComment(id, engineerDetails.getEngineer(), comment);

        return "redirect:/collisions/" + id;
    }
}
