package com.shachi.shachihouse.exception;

import com.shachi.shachihouse.utils.Common;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FileException.class)
    public String handleFileException(FileException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "redirect:"+ Common.urlFileException;
    }


    @ExceptionHandler(RuntimeExceptionCustom.class)
    public String handleRuntimeException(RuntimeExceptionCustom ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "redirect:"+ Common.urlRuntimeExceptionCustom;
    }
}
