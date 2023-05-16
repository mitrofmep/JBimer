package ru.jbimer.core.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.jbimer.core.models.Collision;
import ru.jbimer.core.models.Engineer;
import ru.jbimer.core.models.Project;
import ru.jbimer.core.services.CollisionsService;
import ru.jbimer.core.services.EngineersService;
import ru.jbimer.core.services.ProjectService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EngineersService engineersService;

    @MockBean
    private CollisionsService collisionsService;

    @MockBean
    private ProjectService projectService;
}