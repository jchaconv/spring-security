package com.springcloud.couponservice.security;

/*import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    /* Inicio Seccion 6 */
    /* Inicio Seccion 9 - Security Testing */
    //Se comenta y se modifica para avanzar con los tests
    //SecurityContextRepository securityContextRepository;
    @Autowired
    HttpSessionSecurityContextRepository securityContextRepository;
    /* Fin Seccion 9 - Security Testing */
    //public boolean login(String username, String password) {
    /* Fin Seccion 6 */
    @Override
    public boolean login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        authenticationManager.authenticate(token);
        if(token.isAuthenticated()) {
            /* Inicio Seccion 6 */
            //SecurityContextHolder.getContext().setAuthentication(token);
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(token);
            securityContextRepository.saveContext(context, request, response);
            /* Fin Seccion 6 */
        }
        return false;
    }
}
