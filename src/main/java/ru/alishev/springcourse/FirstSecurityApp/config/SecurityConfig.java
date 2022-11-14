package ru.alishev.springcourse.FirstSecurityApp.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
     * конфигурируем SS,
     * какую форму для логина и пароля использовать
     * как выводить ошибки и тд
     * так же конфигурирует авторизацию
     * (давать или не давать доступ к страницам на основании его статуса)
     *
     * правила
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // отключаем защиту от межсайтовой подделки запросов
        http.csrf().disable()
                // при вызове этого метода все запросы будут проходить через нашу авторизацию
                .authorizeRequests()
                // смотрим какой запрос пришел
                // если на страницу "/auth/login" - то мы должны его пускать
                .antMatchers("/auth/login", "/auth/registration", "/error").permitAll()
                // на все остальные страницы мы не пускаем НЕ аутентифицированных пользователей
                // необходимо пройти аутентификацию
                .anyRequest().authenticated()
                .and()
                // настраиваем форму для логина
                .formLogin().loginPage("/auth/login")
                // обрабатывает post запрос
                .loginProcessingUrl("/process_login")
                // что будет происходить после успешной аутентификации
                .defaultSuccessUrl("/hello", true)
                // в случае не успешной аутентификации
                .failureUrl("/auth/login?error")
                .and()
                // конфигурация logout (у браузера (приложения) стирается сессия и пользователя удаляются кукис)
                .logout()
                .logoutUrl("/logout")
                // redirect если рзлогинелся
                .logoutSuccessUrl("/auth/login");
    }

    /**
     * настраивает аутентификацию
     * даем понять SS, что мы используем этот authProvider для аутентификации пользователей
     */
    @Override
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
