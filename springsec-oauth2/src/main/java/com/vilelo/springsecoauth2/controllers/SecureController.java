package com.vilelo.springsecoauth2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecureController {

    @GetMapping("/secure")
    public String securePage() {
        return "secure.html";
    }


}
