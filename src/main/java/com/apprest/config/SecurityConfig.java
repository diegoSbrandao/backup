package com.apprest.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        log.info("Password encoded {}", passwordEncoder.encode("batistapp2310"));
        auth.inMemoryAuthentication()
                .withUser("diego")
                .password(passwordEncoder.encode("diego123"))
                .roles("USER", "ADMIN")
                .and()
                .withUser("joao")
                .password(passwordEncoder.encode("joao123"))
                .roles("USER", "ADMIN")
                .and()
                .withUser("davi")
                .password(passwordEncoder.encode("davi123"))
                .roles("USER")
                .and()
                .withUser("renato")
                .password(passwordEncoder.encode("renato123"))
                .roles("USER");
    }
}

