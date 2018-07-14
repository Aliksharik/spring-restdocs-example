package kz.pifagor.news.archive.controller;

import kz.pifagor.news.archive.exceptions.ExceptionResponse;
import kz.pifagor.news.archive.exceptions.NewsNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionController{

    @ExceptionHandler(NewsNotFoundException.class)
    public @ResponseBody ResponseEntity<ExceptionResponse> handleNewsNotFoundException(NewsNotFoundException e) throws Exception {
        return createResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody ResponseEntity<ExceptionResponse> processValidationError(
            MethodArgumentNotValidException exception) {

        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(field -> String.format("%s %s", field.getField(), field.getDefaultMessage()))
                .collect(Collectors.joining("\n"));

        return createResponse(HttpStatus.BAD_REQUEST, message);
    }

    private ResponseEntity<ExceptionResponse> createResponse(HttpStatus badRequest, String message) {
        ExceptionResponse eR = new ExceptionResponse(badRequest.value(), badRequest.getReasonPhrase(), message);
        return new ResponseEntity<>( eR , badRequest);
    }

}
