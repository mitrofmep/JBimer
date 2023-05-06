package ru.jbimer.core.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.jbimer.core.models.Collision;
import ru.jbimer.core.models.Engineer;
import ru.jbimer.core.models.HtmlReportData;
import ru.jbimer.core.repositories.CollisionsRepository;
import ru.jbimer.core.repositories.StorageRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class HtmlReportService {

    private final StorageRepository storageRepository;
    private final CollisionsRepository collisionsRepository;
    private final CollisionsService collisionsService;

    @Autowired
    public HtmlReportService(StorageRepository storageRepository, CollisionsRepository collisionsRepository, CollisionsService collisionsService) {
        this.storageRepository = storageRepository;
        this.collisionsRepository = collisionsRepository;
        this.collisionsService = collisionsService;
    }


    public void uploadFile(MultipartFile file, Engineer engineer) throws IOException {
        File tempFile = File.createTempFile("temp", null);
        file.transferTo(tempFile);
        Document doc = Jsoup.parse(tempFile, "UTF-8", "");
        tempFile.delete();

        HtmlReportData data = HtmlReportData.builder()
                .name(file.getOriginalFilename())
                .uploadedAt(new Date())
                .engineer(engineer)
                .data(doc.html()).build();


        storageRepository.save(data);

        List<Collision> collisions = parse(doc, file.getOriginalFilename());

        collisionsService.saveAll(collisions);
    }

    private List<Collision> parse(Document doc, String filename) {
        String regexDisciplines = "^(\\p{L}+)-(\\p{L}+)\\.html$";

        List<Collision> collisionList = new ArrayList<>();

        Pattern pattern = Pattern.compile(regexDisciplines);
        Matcher matcher = pattern.matcher(filename);
        String id1 = null;
        String id2 = null;
        String discipline1 = null;
        String discipline2 = null;
        String elname1 = null;
        String elname2 = null;
        if (matcher.matches()) {
            discipline1 = matcher.group(1);
            discipline2 = matcher.group(2);
        }
        Elements elements = doc.getElementsByTag("tr");
        if (elements.size() < 2) System.out.println("Empty report");
        else {
            for (int i = 1; i < elements.size(); i++) {
                String col1 = elements.get(i).getElementsByTag("td").get(1).ownText();
                String col2 = elements.get(i).getElementsByTag("td").get(2).ownText();

                String[] strings = col1.split(":");
                if (strings.length == 4) {
                    elname1 = strings[0] + " - " + strings[1] + " - " + strings[2];
                } else elname1 = strings[1] + " - " + strings[2] + " - " + strings[3];
                id1 = strings[strings.length - 1].substring(5);

                strings = col2.split(":");
                if (strings.length == 4) {
                    elname2 = strings[0] + " - " + strings[1] + " - " + strings[2];
                } else elname2 = strings[1] + " - " + strings[2] + " - " + strings[3];
                id2 = strings[strings.length - 1].substring(5);

                Collision collision = Collision.builder()
                        .discipline1(discipline1)
                        .discipline2(discipline2)
                        .elementName1(elname1)
                        .elementName2(elname2)
                        .elementId1(id1)
                        .elementId2(id2)
                        .status("Активно")
                        .createdAt(new Date())
                        .build();
                collisionList.add(collision);
            }
        }
        return collisionList;
    }
}
