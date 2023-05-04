package ru.jbimer.core.controllers;

import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.jbimer.core.security.EngineerDetails;
import ru.jbimer.core.services.EngineerDetailsService;
import ru.jbimer.core.services.StorageService;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class HtmlReportController {

    private final StorageService service;

    @Autowired
    public HtmlReportController(StorageService service) {
        this.service = service;
    }

    @GetMapping
    public String index() {

        return "files/index";
    }

    @PostMapping
    public String uploadFile(@RequestParam("file")MultipartFile file) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EngineerDetails engineerDetails = (EngineerDetails) authentication.getPrincipal();
        service.uploadFile(file, engineerDetails.getEngineer());

        return "files/index";
    }
}
