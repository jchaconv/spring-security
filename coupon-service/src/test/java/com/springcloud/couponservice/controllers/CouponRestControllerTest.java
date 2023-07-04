package com.springcloud.couponservice.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CouponRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testGetCouponForbidden() throws Exception {
        mockMvc.perform(get("/coupon/api/coupons/SUPERSALE"))
                .andExpect(status().isOk());
    }
}