package ru.jbimer.core.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ProjectExceptionHandler {

    @ExceptionHandler(EmptyEngineersListException.class)
    public ModelAndView handleEmptyEngineersListException(EmptyEngineersListException e, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("projects/new");
        modelAndView.addObject("errorMessage", "Choose at least 1 engineer");
        return modelAndView;
    }
}
