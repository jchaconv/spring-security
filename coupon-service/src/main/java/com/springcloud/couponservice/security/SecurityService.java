package com.springcloud.couponservice.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface SecurityService {

    //boolean login(String username, String password, HttpServletRequest request, HttpServletResponse response);

    //Seccion 6
    boolean login(String username, String password, HttpServletRequest request, HttpServletResponse response);

}
