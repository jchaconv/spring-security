package com.vilelo.springsec_section2.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {


    @GetMapping("/myBalance")
    public String getBalanceDetails(){
        return "Balance details from the DB";
    }

}
