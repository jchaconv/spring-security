package com.springcloud.couponservice.controllers;

import com.springcloud.couponservice.model.Coupon;
import com.springcloud.couponservice.repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/coupon/api")
public class CouponController {

    @Autowired
    private CouponRepository repository;

    @RequestMapping(value = "/coupons", method = RequestMethod.POST)
    public Coupon create(@RequestBody Coupon coupon) {
        return repository.save(coupon);
    }

    @GetMapping("/coupons/{code}")
    public ResponseEntity<Object> getCoupon(@PathVariable String code) {
        if (!isValidCouponCode(code)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid coupon code");
        }

        Coupon coupon = repository.findByCode(code);
        if (coupon == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(coupon);
    }

    /*
    @RequestMapping(value = "/coupons/{code}", method = RequestMethod.GET)
    public Coupon getCoupon(@PathVariable("code") String code) {
        return repository.findByCode(code);
    }

    @GetMapping("/showCreateCoupon")
    public String showCreateCoupon() {
        return "createCoupon";
    }

    @PostMapping("/saveCoupon")
    public String save(Coupon coupon) {
        repository.save(coupon);
        return "createResponse";
    }

    @GetMapping("/showGetCoupon")
    public String showGetCoupon() {
        return "getCoupon";
    }

    @PostMapping("/getCoupon")
    public ModelAndView getCoupon(String code) {
        ModelAndView mav = new ModelAndView("couponDetails");
        mav.addObject(repository.findByCode(code));
        return mav;
    }
    */

    private boolean isValidCouponCode(String code) {
        return code.matches("[A-Z]+");
    }

}
