package com.fillswim.spring.security.configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

import javax.sql.DataSource;

// Создание конфигурации для Spring Security
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    final
    DataSource dataSource;

    public MySecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Необходимо переоределить метод для определения в нем имен, паролей и ролей пользователей
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Создание пользователей и паролей In-Memory
        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();

        // Определение пользователей
        // Будет проводиться сравнение имя пользователя и пароля и тем, что мы сейчас пропишем
        // Будет получаться из базы данных
//        auth.inMemoryAuthentication()
//                .withUser(userBuilder.username("oleg")
//                        .password("oleg")
//                        .roles("EMPLOYEE"))
//                .withUser(userBuilder.username("elena")
//                        .password("elena")
//                        .roles("HR"))
//                .withUser(userBuilder.username("ivan")
//                        .password("ivan")
//                        .roles("MANAGER", "HR"));

        // Информация о пользователях и паролях будет получаться из базы данных из таблиц users и authorities
        auth.jdbcAuthentication().dataSource(dataSource);


    }

    // Необходим для авторизации запросов
    // Будет давться разрешение для конкретного URL конкретным ролям
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/").hasAnyRole("EMPLOYEE", "HR", "MANAGER") // Начальная страница доступна для всех
                .antMatchers("/hr_info").hasRole("HR") // Страница с URL /hr_info доступна только HR
                .antMatchers("/manager_info").hasRole("MANAGER")
                .and().formLogin().permitAll();


    }
}
