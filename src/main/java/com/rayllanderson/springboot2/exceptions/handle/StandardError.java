package com.rayllanderson.springboot2.exceptions.handle;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StandardError {

    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private Object message;
    private String path;
}
