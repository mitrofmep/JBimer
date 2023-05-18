package ru.jbimer.core.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.jbimer.core.dao.CollisionDAO;
import ru.jbimer.core.exception.InvalidFileNameException;
import ru.jbimer.core.models.Collision;
import ru.jbimer.core.models.Engineer;
import ru.jbimer.core.models.HtmlReportData;
import ru.jbimer.core.models.Project;
import ru.jbimer.core.repositories.CollisionsRepository;
import ru.jbimer.core.repositories.HtmlReportDataRepository;
import ru.jbimer.core.util.HtmlReportUtil;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class HtmlReportService {

    private final HtmlReportDataRepository htmlReportDataRepository;
    private final CollisionsRepository collisionsRepository;
    private final CollisionsService collisionsService;
    private final HtmlReportUtil htmlReportUtil;
    private final CollisionDAO collisionDAO;

    @Autowired
    public HtmlReportService(HtmlReportDataRepository htmlReportDataRepository, CollisionsRepository collisionsRepository, CollisionsService collisionsService, HtmlReportUtil htmlReportUtil, CollisionDAO collisionDAO) {
        this.htmlReportDataRepository = htmlReportDataRepository;
        this.collisionsRepository = collisionsRepository;
        this.collisionsService = collisionsService;
        this.htmlReportUtil = htmlReportUtil;
        this.collisionDAO = collisionDAO;
    }


    public int uploadFile(MultipartFile file, Engineer engineer, Project project) throws IOException {
        if (!htmlReportUtil.nameIsValid(file.getOriginalFilename())) throw new InvalidFileNameException(project);
        File tempFile = File.createTempFile("temp", null);
        file.transferTo(tempFile);
        Document doc = Jsoup.parse(tempFile, "UTF-8", "");
        tempFile.delete();

        HtmlReportData data = HtmlReportData.builder()
                .name(file.getOriginalFilename())
                .uploadedAt(new Date())
                .engineer(engineer)
                .project(project)
                .data(doc.html()).build();


        htmlReportDataRepository.save(data);

        List<Collision> collisions = htmlReportUtil.parse(doc, file.getOriginalFilename(), project);

        collisionsService.saveAll(collisions, project);

        return collisions.size();
    }

    public List<HtmlReportData> findAllByProject(Project project) {
        return htmlReportDataRepository.findAllByProject(project);
    }
}
