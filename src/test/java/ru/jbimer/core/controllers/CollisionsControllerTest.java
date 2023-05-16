package ru.jbimer.core.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import ru.jbimer.core.models.Collision;
import ru.jbimer.core.models.Engineer;
import ru.jbimer.core.services.CollisionsService;
import ru.jbimer.core.services.EngineersService;
import ru.jbimer.core.services.HtmlReportService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CollisionsController.class)
public class CollisionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CollisionsService collisionsService;

    @MockBean
    private EngineersService engineersService;

    @MockBean
    private HtmlReportService htmlReportService;

    @Test
    public void testIndex() throws Exception {
        int projectId = 1;
        List<Collision> collisions = new ArrayList<>();
        collisions.add(new Collision());
        Page<Collision> collisionPage = new PageImpl<>(collisions);
        when(collisionsService.findAllWithPagination(projectId, PageRequest.of(0, 10, Sort.by("id"))))
                .thenReturn(collisionPage);
        mockMvc.perform(get("/projects/" + projectId + "/collisions"))
                .andExpect(status().isOk())
                .andExpect(view().name("collisions/index"))
                .andExpect(model().attribute("collisions", hasSize(1)))
                .andExpect(model().attribute("currentPage", equalTo(1)))
                .andExpect(model().attribute("totalItems", equalTo(1L)))
                .andExpect(model().attribute("totalPages", equalTo(1)))
                .andExpect(model().attribute("pageSize", equalTo(10)))
                .andExpect(model().attribute("project_id", equalTo(projectId)));
    }

    @Test
    void testShow() throws Exception {
        Collision collision = new Collision();
        when(collisionsService.findOneAndEngineer(1)).thenReturn(collision);

        mockMvc.perform(get("/projects/1/collisions/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("collisions/show"))
                .andExpect(model().attribute("collision", equalTo(collision)))
                .andExpect(model().attribute("comments", equalTo(collision.getComments())))
                .andExpect(model().attributeExists("role"));
    }

    @Test
    void testUploadCollisionsReportPage() throws Exception {
        mockMvc.perform(get("/projects/1/collisions/upload"))
                .andExpect(status().isOk())
                .andExpect(view().name("collisions/upload"));
    }

    @Test
    void testUpload() throws Exception {
        when(htmlReportService.uploadFile(any(MultipartFile.class), any(Engineer.class))).thenReturn(2);

        mockMvc.perform(multipart("/projects/1/collisions").file("file", "test file content".getBytes()))
                .andExpect(status().isOk())
                .andExpect(view().name("collisions/upload"))
                .andExpect(model().attribute("collisionsUploaded", equalTo(2)));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/projects/1/collisions/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/collisions"));
    }

    @Test
    void testRelease() throws Exception {
        mockMvc.perform(patch("/projects/1/collisions/1/release"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/collisions/1"));
    }


    @Test
    void testMarkAsFake() throws Exception {
        mockMvc.perform(get("/projects/1/collisions/1/fake"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/collisions/1"));
    }

    @Test
    void testMarkAsNotFake() throws Exception {
        mockMvc.perform(get("/projects/1/collisions/1/not-fake"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/collisions/1"));
    }
}