package com.system.announcement.infra.config.security;

import com.system.announcement.infra.token.TokenService;
import com.system.announcement.services.AuthorizationService;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenservice;
    private final AuthorizationService authorizationService;

    public SecurityFilter(TokenService tokenservice, AuthorizationService authorizationService) {
        this.tokenservice = tokenservice;
        this.authorizationService = authorizationService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        var token = this.recoverToken(request);

        if(token != null){
            var email = tokenservice.validateToken(token);

            if(!email.isEmpty()){
                UserDetails user = authorizationService.loadUserByUsername(email);

                var authentication = new UsernamePasswordAuthenticationToken(user,
                        null, user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;

        return authHeader.replace("Bearer ", "");
    }
}
