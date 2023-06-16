package com.ikonnikova.restrailway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * Конфигурационный класс для настройки Spring Security.
 */

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.admin.name}")
    private String adminUsername;

    @Value("${spring.security.admin.password}")
    private String adminPassword;

    @Value("${spring.security.guest.name}")
    private String guestName;

    @Value("${spring.security.guest.password}")
    private String guestPassword;

    @Value("${spring.security.employee.name}")
    private String employeeName;

    @Value("${spring.security.employee.password}")
    private String employeePassword;

    @Value("${spring.security.user.role1}")
    private String adminRole;

    @Value("${spring.security.user.role2}")
    private String guestRole;

    @Value("${spring.security.user.role3}")
    private String employeeRole;

    /**
     * Настраивает аутентификацию пользователей.
     *
     * @param auth Объект AuthenticationManagerBuilder для настройки аутентификации.
     * @throws Exception Если произошла ошибка при настройке аутентификации.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser(adminUsername)
                .password(adminPassword)
                .roles(adminRole)

                .and()
                .withUser(guestName)
                .password(guestPassword)
                .roles(guestRole)

                .and()
                .withUser(employeeName)
                .password(employeePassword)
                .roles(employeeRole);
    }

    /**
     * Настраивает доступ к различным URL-адресам веб-приложения в зависимости от ролей пользователей.
     *
     * @param http Объект HttpSecurity для настройки доступа.
     * @throws Exception Если произошла ошибка при настройке доступа.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/waybills/**").hasRole(adminRole)
                .antMatchers("/api/stations/**").hasAnyRole(adminRole, guestRole)
                .antMatchers("/api/station-models/**").hasAnyRole(adminRole, guestRole)
                .antMatchers("/api/station-tracks/**").hasAnyRole(adminRole, guestRole)
                .antMatchers("/api/wagons/**").hasAnyRole(adminRole, guestRole, employeeRole)
                .antMatchers("/api/cargos/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin();
    }

    /**
     * Настраивает игнорирование доступа к определенным ресурсам веб-приложения.
     *
     * @param web Объект WebSecurity для настройки игнорирования доступа.
     * @throws Exception Если произошла ошибка при настройке игнорирования доступа.
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }
}




