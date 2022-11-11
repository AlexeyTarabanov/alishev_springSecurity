package ru.alishev.springcourse.FirstSecurityApp.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.alishev.springcourse.FirstSecurityApp.services.PersonDetailsService;

/**
 * главный класс, в котором будем настраивать SS
 * */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    /**
     * настраивает аутентификацию
     * даем понять SS, что мы используем этот authProvider для аутентификации пользователей
     */
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService);
    }

    /**
     * указываем, с помощью какого алгоритма мы шифруем свой пароль
     * */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        // пароль никак не шифруется
        return NoOpPasswordEncoder.getInstance();
    }
}
