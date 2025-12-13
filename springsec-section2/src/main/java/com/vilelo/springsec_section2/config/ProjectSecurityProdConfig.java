package com.vilelo.springsec_section2.config;

import com.vilelo.springsec_section2.exceptionhandling.CustomAccessDeniedHandler;
import com.vilelo.springsec_section2.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("prod")
public class ProjectSecurityProdConfig {

    @Bean
    SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {

        http.sessionManagement(smc -> smc.invalidSessionUrl("/invalidSession"))
                .redirectToHttps((https) -> https.requestMatchers(AnyRequestMatcher.INSTANCE)) //only https
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                .requestMatchers("/myAccount", "/myBalance", "/myCards", "/myLoans").authenticated()
                .requestMatchers("/notices", "/contact", "/error", "/register", "/invalidSession").permitAll());

        http.formLogin(withDefaults());

        http.httpBasic(hbc ->
                hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));

        http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
