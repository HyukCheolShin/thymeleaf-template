package com.example.template.common.exception;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice(annotations = Controller.class)
public class ViewExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ViewExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException(ResourceNotFoundException ex, Model model) {
        logger.error("View Resource not found: ", ex);
        model.addAttribute("status", 404);
        model.addAttribute("message", ex.getMessage());
        return "error/error";
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, Model model) {
        logger.error("View Type Mismatch: ", ex);
        model.addAttribute("status", 400);
        model.addAttribute("message", "Invalid parameter: " + ex.getName());
        return "error/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        logger.error("View Unhandled exception: ", ex);
        model.addAttribute("status", 500);
        model.addAttribute("message", "An unexpected error occurred. Please contact the administrator.");
        return "error/error";
    }
}
