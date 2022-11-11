package ru.alishev.springcourse.FirstSecurityApp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.alishev.springcourse.FirstSecurityApp.models.Person;

import java.util.Collection;

/**
 * класс-обертка для сущности Person
 * (когда мы работаем со Spring Security мы работаем не напрямую с сущностью,
 * а с классом-оберткой)
 * он должен реализовывать интерфейс UserDetailsService
 * чтобы был стандарт,
 * чтобы данные от пользователя получались каким-то стандартным способом
 * */

public class PersonDetails implements UserDetails {

    private final Person person;

    public PersonDetails(Person person) {
        this.person = person;
    }

    /**
     * получает роли, которые есть у пользователя и
     * возвращает коллекцию (список) прав
     * */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return null;
    }

    @Override
    public String getPassword() {
        return person.getPassword();
    }

    @Override
    public String getUsername() {
        return person.getUsername();
    }

    /**
     * текущая сущность активна
     * */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * учетная запись не заблокирована
     * */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * срок действия учетных данных не истек
     * пароль не просрочен
     * */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * аккаунт включен и работает
     * */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * нужен, чтобы получать данные аутентифицированного пользователя
     * */
    public Person getPerson() {
        return person;
    }
}
