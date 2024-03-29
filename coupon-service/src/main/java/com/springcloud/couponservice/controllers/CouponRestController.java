package com.springcloud.couponservice.controllers;

import com.springcloud.couponservice.model.Coupon;
import com.springcloud.couponservice.repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupon/api")
/* Inicio Seccion 8 - CORS */
@CrossOrigin
/* Fin Seccion 8 - CORS */
public class CouponRestController {

    @Autowired
    private CouponRepository repository;

    @RequestMapping(value = "/coupons", method = RequestMethod.POST)
    /* Inicio Seccion 9 - Security Testing */
    @PreAuthorize("hasRole('ADMIN')")
    /* Fin Seccion 9 - Security Testing */
    public Coupon create(@RequestBody Coupon coupon) {
        return repository.save(coupon);
    }

    @GetMapping("/coupons/{code}")
    /* Inicio Seccion 9 - Security Testing */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    /* Fin Seccion 9 - Security Testing */
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
