package com.omniesoft.commerce.support.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoriesController {


    @GetMapping(path = "status", produces = MediaType.APPLICATION_JSON_VALUE)
    public String status() {
        return "{}";
    }
}
