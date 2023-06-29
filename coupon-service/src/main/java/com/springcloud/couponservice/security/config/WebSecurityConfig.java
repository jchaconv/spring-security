package com.springcloud.couponservice.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class WebSecurityConfig {

    /*Inicio Sección 6*/
    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository());
    }

    AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }
    /*Fin Sección 6*/

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        //httpSecurity.httpBasic();
        /* Inicio Seccion 6 */
        //httpSecurity.formLogin();
        /* Fin Seccion 6 */
        httpSecurity
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/coupon/api/coupons/**", "/", "/showGetCoupon", "/getCoupon").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/showCreateCoupon", "/createCoupon", "/createResponse").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/coupon/api/coupons", "/saveCoupon").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/getCoupon").hasAnyRole("USER", "ADMIN")
                /* Inicio Seccion 6 */
                .requestMatchers("/", "/login", "/showReg", "/registerUser").permitAll()
                .and().logout().logoutSuccessUrl("/")
                /* Fin Seccion 6 */
                /* Inicio Seccion 7 */
                //.and().csrf().disable();
                ;
                /* Fin Seccion 7 */

        /* Inicio Seccion 7 */
        httpSecurity.csrf(csrfCustomizer -> {
            csrfCustomizer.ignoringRequestMatchers("/coupon/api/coupons/**");
            RequestMatcher requestMatchers = new RegexRequestMatcher("/coupon/api/coupons/**", "POST");
            requestMatchers = new MvcRequestMatcher(new HandlerMappingIntrospector(), "/getCoupon");
            csrfCustomizer.ignoringRequestMatchers(requestMatchers);
        });
        /* Fin Seccion 7 */

        /*Inicio Sección 6*/
        httpSecurity.securityContext(context -> context.requireExplicitSave((true)));
        /*Fin Sección 6*/

        return httpSecurity.build();
    }

}
