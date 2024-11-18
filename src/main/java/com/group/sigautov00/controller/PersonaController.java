package com.group.sigautov00.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persona")
public class PersonaController {
    @GetMapping("")
    public String getPersona() {
        return "{ \"name\": \"John\", \"age\": 1 }";
    }
}
