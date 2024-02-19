package com.shachi.shachihouse.exception;

import com.shachi.shachihouse.utils.Common;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodHandleExceptionValidation(MethodArgumentNotValidException e){
        List<String> fieldsErrors = e.getBindingResult().getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).collect(Collectors.toList());
        return new ResponseEntity<>(new Object[]{LocalDate.now(),fieldsErrors}, HttpStatus.BAD_REQUEST);
    }
}
