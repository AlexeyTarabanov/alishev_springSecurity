package ru.alishev.springcourse.FirstSecurityApp.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.alishev.springcourse.FirstSecurityApp.security.PersonDetails;


@Controller
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }


    /**
     * из сессии достанем пользователя, он будет помещен в поток (authentication)
     * задаункастим объект principal в класс PersonDetails
     * и на нем уже сможем получить сущность Person
     *
     * все, что положим в результате аутентификации в объект UsernamePasswordAuthenticationToken,
     * ко всему этому мы будем иметь доступ при каждом следующем запросе этого человека к нашему серверу
     * благодаря сессиям и куки
     * */
    @GetMapping("/showUserInfo")
    public String showUserInfo() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        System.out.println(personDetails.getPerson());
        // Person {id=1, username='test_user_1', yearOfBirth=1960, password='test_password'}


        return "hello";
    }
}
