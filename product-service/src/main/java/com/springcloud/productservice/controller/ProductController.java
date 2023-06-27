package com.springcloud.productservice.controller;

import com.springcloud.productservice.dto.Coupon;
import com.springcloud.productservice.model.Product;
import com.springcloud.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${couponService.url}")
    private String couponUrl;

    @PostMapping("/product")
    public Product createProduct(@RequestBody Product product) {

        String username = "doug@bailey.com";
        String password = "doug";
        String authCredentials = username + ":" + password;
        byte[] base64AuthCredentials = Base64.getEncoder().encode(authCredentials.getBytes());
        String authHeader = "Basic " + new String(base64AuthCredentials);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authHeader);

        //Coupon coupon = restTemplate.getForObject(couponUrl + product.getCouponCode(), Coupon.class);
        Coupon coupon = restTemplate.exchange(couponUrl + product.getCouponCode(), HttpMethod.GET, new HttpEntity<>(headers), Coupon.class).getBody();
        System.out.println("coupon.getDiscount():" + coupon.getDiscount());

        product.setPrice(product.getPrice().subtract(coupon.getDiscount()));
        return productRepository.save(product);
    }

    @GetMapping("/product/{id}")
    public Optional<Product> getProductById(@PathVariable("id") Long productId) {
        return productRepository.findById(productId);
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /*private boolean isValidCouponCode(String code) {
        return code.matches("[A-Z]+");
    }*/

}
