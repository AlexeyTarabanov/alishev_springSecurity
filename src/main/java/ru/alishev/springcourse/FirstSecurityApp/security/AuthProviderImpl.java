package ru.alishev.springcourse.FirstSecurityApp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.alishev.springcourse.FirstSecurityApp.services.PersonDetailsService;

import java.util.Collections;

/**
 * отвечает за логику аутентификации
 * */

@Component
public class AuthProviderImpl implements AuthenticationProvider {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public AuthProviderImpl(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    /**
     * здесь будет лежать объект principal с данными о пользователе (personDetails)
     * */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // получаем имя и пароль с формы
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails personDetails = personDetailsService.loadUserByUsername(username);
        if (!password.equals(personDetails.getPassword()))
            throw new BadCredentialsException("Incorrect password");

        // personDetails - человек, пароль, список прав
        return new UsernamePasswordAuthenticationToken(personDetails, password, Collections.emptyList());
    }

    /**
     * Возвращает true, если этот AuthenticationProvider поддерживает указанный объект Authentication.
     * */
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
