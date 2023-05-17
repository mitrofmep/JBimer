package ru.jbimer.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.jbimer.core.models.Project;
import ru.jbimer.core.security.EngineerDetails;
import ru.jbimer.core.services.HtmlReportService;
import ru.jbimer.core.services.ProjectService;

import java.io.IOException;

@Controller
@RequestMapping("/reports")
public class ReportUploadController {

    private final HtmlReportService htmlReportService;
    private final ProjectService projectService;

    @Autowired
    public ReportUploadController(HtmlReportService htmlReportService, ProjectService projectService) {
        this.htmlReportService = htmlReportService;
        this.projectService = projectService;
    }

    @PostMapping()
    public String upload(@RequestParam("file") MultipartFile file,
                         Model model,
                         @RequestParam("projectId") int project_id) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EngineerDetails engineerDetails = (EngineerDetails) authentication.getPrincipal();

        Project project = projectService.findOne(project_id);

        int collisions = htmlReportService.uploadFile(file, engineerDetails.getEngineer(), project);
        model.addAttribute("collisionsUpdated", collisions);
        model.addAttribute("project", project);

        return "collisions/upload";
    }

}
