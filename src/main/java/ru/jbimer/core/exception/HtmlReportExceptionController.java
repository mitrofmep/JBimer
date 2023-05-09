package ru.jbimer.core.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class HtmlReportExceptionController {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        if (ex instanceof InvalidFileNameException) {
            modelAndView.setViewName("collisions/upload");
            modelAndView.addObject("errorMessage", "Invalid file name. Please, try again");
        }
        return modelAndView;
    }
}
