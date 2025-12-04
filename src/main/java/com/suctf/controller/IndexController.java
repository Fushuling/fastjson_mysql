package com.suctf.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "FastJSON Parser API\nPOST /json/parse with JSON data to parse";
    }
}

