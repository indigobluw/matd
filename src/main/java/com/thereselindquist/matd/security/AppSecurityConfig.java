package com.thereselindquist.matd.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AppSecurityConfig {
    private final AppPasswordConfig bcrypt; //kanske bara behöver denna om man ska ha daoauthenticator provider

    @Autowired
    public AppSecurityConfig(AppPasswordConfig bcrypt) {
        this.bcrypt = bcrypt;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers("/", "/aboutus", "/login", "/register", "/static/**").permitAll()
                                .requestMatchers("/admin", "/logout").hasRole("ADMIN")
                                .requestMatchers("/user", "/createitem", "/showitems", "/logout").hasRole("USER")
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(formLogin -> formLogin
                                .loginPage("/login")
                        //.successHandler(myAuthenticationSuccessHandler())
                )
                .rememberMe(rememberMe -> rememberMe
                        .rememberMeParameter("remember-me")
                        .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                        .key("key")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                );
        //.authenticationProvider(authenticationOverride()); Bara om man har dao?

        return http.build();
    }
}
