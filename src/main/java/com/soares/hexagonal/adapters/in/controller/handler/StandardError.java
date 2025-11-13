package com.soares.hexagonal.adapters.in.controller.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StandardError {

    private Long timestemp;
    private Integer status;
    private String message;
    private String path;

}
