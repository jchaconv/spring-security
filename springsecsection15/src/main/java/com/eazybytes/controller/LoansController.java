package com.eazybytes.controller;

import com.eazybytes.model.Customer;
import com.eazybytes.model.Loans;
import com.eazybytes.repository.CustomerRepository;
import com.eazybytes.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoansController {

    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;

    @PostAuthorize("hasRole('ROOT')")
    @GetMapping("/myLoans")
    public List<Loans> getLoanDetails(@RequestParam String email) {
        Customer customer = customerRepository.findByEmail(email).orElse(null);
        List<Loans> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(customer.getId());
        if (loans != null) {
            return loans;
        } else {
            return null;
        }
    }

}
