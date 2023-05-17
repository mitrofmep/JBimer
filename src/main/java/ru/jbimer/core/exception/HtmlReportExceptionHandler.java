package ru.jbimer.core.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class HtmlReportExceptionHandler {

    @ExceptionHandler(InvalidFileNameException.class)
    public ModelAndView handleInvalidFileNameException(InvalidFileNameException ex, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("collisions/upload");
        modelAndView.addObject("errorMessage", "Invalid file name. Please, try again");
        modelAndView.addObject("project", ex.getProject());
        return modelAndView;
    }
}
