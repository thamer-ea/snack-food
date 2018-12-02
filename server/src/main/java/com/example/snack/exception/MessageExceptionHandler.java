package com.example.snack.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.google.gson.Gson;

@ControllerAdvice
public class MessageExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(MethodArgumentNotValidException exception) {

        String errorMsg = exception.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse(exception.getMessage());

        return ErrorResponse.builder().message(errorMsg).build();
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(HttpMessageNotReadableException exception) {
        return ErrorResponse.builder().message("Invalid body format.").build();
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ErrorResponse handleException(HttpMediaTypeNotSupportedException exception) {
        return ErrorResponse.builder().message("Unsupported Content-Type.").build();
    }

    @ExceptionHandler(value = ServletRequestBindingException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(ServletRequestBindingException exception) {
        return ErrorResponse.builder().message(exception.getMessage()).build();
    }

    @ExceptionHandler(value = MessageException.class)
    @ResponseBody
    public ResponseEntity<?> handleMessageException(MessageException exception) {
        ErrorMessageTO errorMessageTO = handleError(exception);
        Gson gson = new Gson();
        return new ResponseEntity(gson.toJson(errorMessageTO), exception.getStatusCode());
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handleException(HttpRequestMethodNotSupportedException exception) {
        return ErrorResponse.builder().message(exception.getMessage()).build();
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleException(NoHandlerFoundException exception) {
        return ErrorResponse.builder().message(exception.getMessage()).build();
    }

    private ErrorMessageTO handleError(MessageException exception) {
        ErrorMessageTO errorMessageTO = new ErrorMessageTO();
        errorMessageTO.setErrorMessage(exception.getMessage());
        return errorMessageTO;
    }

}
