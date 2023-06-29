package com.springcloud.couponservice.controllers;

import com.springcloud.couponservice.model.User;
import com.springcloud.couponservice.repositories.UserRepository;
import com.springcloud.couponservice.security.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    /*Inicio Sección 6*/
    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/showReg")
    public String showRegistrationPage() {
        return "registerUser";
    }

    @PostMapping("/registerUser")
    public String register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return "login";
    }
    /*Fin Sección 6*/

    @GetMapping("/")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/")
    public String login(String email, String password, HttpServletRequest request, HttpServletResponse response) {
        /* Inicio Seccion 6 */
        //public String login(String email, String password) {
        //boolean loginResponse = securityService.login(email, password);
        /* Fin Seccion 6 */
        boolean loginResponse = securityService.login(email, password, request, response);
        if(loginResponse) {
            return "index";
        }
        return "login";
    }


}
