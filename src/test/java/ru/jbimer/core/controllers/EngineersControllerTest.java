package ru.jbimer.core.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.jbimer.core.models.Collision;
import ru.jbimer.core.models.Engineer;
import ru.jbimer.core.services.CollisionsService;
import ru.jbimer.core.services.EngineersService;
import ru.jbimer.core.services.HtmlReportService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EngineersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CollisionsService collisionsService;

    @MockBean
    private EngineersService engineersService;

    @MockBean
    private HtmlReportService htmlReportService;

    @MockBean
    private EngineersController engineersController;

    @Test
    public void testIndex() throws Exception {
        List<Engineer> engineers = new ArrayList<>();
        engineers.add(new Engineer());
        when(engineersService.findAllSortedByCollisionsSize()).thenReturn(engineers);
        Model model = new ExtendedModelMap();

        String viewName = engineersController.index(model);

        assertEquals("engineers/index", viewName);
        assertTrue(model.containsAttribute("engineers"));
        assertSame(engineers, model.getAttribute("engineers"));
        verify(engineersService).findAllSortedByCollisionsSize();
    }

    @Test
    public void testShow() throws Exception {
        int id = 1;
        Engineer engineer = new Engineer();
        Set<Collision> collisions = new HashSet<>();
        engineer.setCollisions((List<Collision>) collisions);
        when(engineersService.findByIdFetchCollisions(id)).thenReturn(engineer);
        Model model = new ExtendedModelMap();

        String viewName = engineersController.show(id, model);

        assertEquals("engineers/show", viewName);
        assertTrue(model.containsAttribute("engineer"));
        assertSame(engineer, model.getAttribute("engineer"));
        assertTrue(model.containsAttribute("collisions"));
        assertSame(collisions, model.getAttribute("collisions"));
        verify(engineersService).findByIdFetchCollisions(id);
    }

    @Test
    public void testEdit() throws Exception {
        int id = 1;
        Engineer engineer = new Engineer();
        when(engineersService.findOne(id)).thenReturn(engineer);
        Model model = new ExtendedModelMap();

        String viewName = engineersController.edit(model, id);

        assertEquals("engineers/edit", viewName);
        assertTrue(model.containsAttribute("engineer"));
        assertSame(engineer, model.getAttribute("engineer"));
        verify(engineersService).findOne(id);
    }

    @Test
    public void testUpdate() throws Exception {
        int id = 1;
        Engineer updatedEngineer = new Engineer();
        updatedEngineer.setId(id);
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        Engineer originalEngineer = mock(Engineer.class);
        when(engineersService.findOne(id)).thenReturn(originalEngineer);

        String viewName = engineersController.update(updatedEngineer, bindingResult, id);

        assertEquals("redirect:/engineers", viewName);
        verify(engineersService).update(id, updatedEngineer, originalEngineer);
    }

    @Test
    public void testDelete() throws Exception {
        int id = 1;
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String viewName = engineersController.delete(id);

        assertEquals("redirect:/logout", viewName);
        verify(engineersService).delete(id);
        verify(authentication).isAuthenticated();
        verify(authentication).setAuthenticated(false);
    }

}