package com.bookstore.user_exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class UserException extends RuntimeException{

    String message;
    HttpStatus status;

}
