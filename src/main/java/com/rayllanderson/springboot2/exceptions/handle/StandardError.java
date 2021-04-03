package com.rayllanderson.springboot2.exceptions.handle;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class StandardError {

    protected LocalDateTime timestamp;
    protected Integer status;
    protected String error;
    protected Object message;
    protected String path;
}
