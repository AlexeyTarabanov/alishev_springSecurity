package ru.alishev.springcourse.FirstSecurityApp.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.alishev.springcourse.FirstSecurityApp.security.AuthProviderImpl;

/**
 * главный класс, в котором будем настраивать SS
 * */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthProviderImpl authProvider;

    @Autowired
    public SecurityConfig(AuthProviderImpl authProvider) {
        this.authProvider = authProvider;
    }

    /**
     * настраивает аутентификацию
     * даем понять SS, что мы используем этот authProvider для аутентификации пользователей
     */
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }
}
