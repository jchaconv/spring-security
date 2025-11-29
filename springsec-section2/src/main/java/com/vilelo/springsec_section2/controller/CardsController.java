package com.vilelo.springsec_section2.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardsController {


    @GetMapping("/myCards")
    public String getCardsDetails(){
        return "Cards details from the DB";
    }

}
