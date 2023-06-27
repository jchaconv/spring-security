package com.springcloud.couponservice.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic();

        httpSecurity
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/coupon/api/coupons/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/coupon/api/coupons").hasRole("ADMIN")
                .and().csrf().disable();

        return httpSecurity.build();
    }

}
