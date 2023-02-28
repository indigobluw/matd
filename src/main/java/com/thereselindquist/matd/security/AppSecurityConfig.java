package com.thereselindquist.matd.security;

import com.thereselindquist.matd.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
    private final UserService userService;

    @Autowired
    public AppSecurityConfig(AppPasswordConfig bcrypt, UserService userService) {
        this.bcrypt = bcrypt;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(requests -> {
                    requests
                            .requestMatchers("/", "/aboutus", "/login", "/register", "/static/**").permitAll()
                            //.requestMatchers().hasRole("ADMIN")
                            .requestMatchers("/createitem", "/user", "/showitems", "/logout", "/admin").authenticated()
                            //.requestMatchers().hasRole("USER")
                            .anyRequest()
                            .authenticated();
                })
                .formLogin(formLogin -> {
                    formLogin
                            .loginPage("/login");
                    //.successHandler(myAuthenticationSuccessHandler())
                })
                .rememberMe(rememberMe -> {
                    rememberMe
                            .rememberMeParameter("remember-me")
                            .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                            .key("key")
                            .userDetailsService(userService);

                })
                .logout(logout -> {
                    logout
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/")
                            .clearAuthentication(true)
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID", "remember-me");
                })
                .authenticationProvider(authenticationOverride());

        return http.build();
    }

    public DaoAuthenticationProvider authenticationOverride() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(bcrypt.bCryptPasswordEncoder());

        return provider;
    }
}
