package com.springcloud.productservice.controller;

import com.springcloud.productservice.dto.Coupon;
import com.springcloud.productservice.model.Product;
import com.springcloud.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
        System.out.println(couponUrl);
        System.out.println("product.getPrice():" + product.getPrice());
        System.out.println("product.getCouponCode():" + product.getCouponCode());
        Coupon coupon = restTemplate.getForObject(couponUrl + product.getCouponCode(), Coupon.class);
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



}
