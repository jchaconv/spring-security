package com.eazybytes.controller;

import com.eazybytes.model.Cards;
import com.eazybytes.model.Customer;
import com.eazybytes.repository.CardsRepository;
import com.eazybytes.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardsController {

    private final CardsRepository cardsRepository;
    private final CustomerRepository customerRepository;

    @GetMapping("/myCards")
    public List<Cards> getCardDetails(@RequestParam String email) {
        Customer customer = customerRepository.findByEmail(email).orElse(null);
        List<Cards> cards = cardsRepository.findByCustomerId(customer.getId());
        if (cards != null ) {
            return cards;
        }else {
            return null;
        }
    }

}
