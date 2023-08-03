package com.bookstore.configurations;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Response {
    String message;
    Object object;
}
