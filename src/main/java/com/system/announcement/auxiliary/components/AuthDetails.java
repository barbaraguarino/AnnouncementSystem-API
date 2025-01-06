package com.system.announcement.auxiliary.components;

import com.system.announcement.exceptions.AuthenticationCredentialsNotFoundException;
import com.system.announcement.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthDetails {

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new AuthenticationCredentialsNotFoundException("Usuário não autenticado.");
        }

        if (!(authentication.getPrincipal() instanceof User)) {
            throw new AuthenticationCredentialsNotFoundException("Autenticação inválida ou não reconhecida.");
        }

        return (User) authentication.getPrincipal();
    }

}