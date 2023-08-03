package com.bookstore.user_exception;

import com.bookstore.configurations.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalUserException extends Exception{

    @Autowired
    Response response;
    @ExceptionHandler
    public ResponseEntity<Response> exceptionMessage (UserException exception){
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response,exception.status);
    }

}
