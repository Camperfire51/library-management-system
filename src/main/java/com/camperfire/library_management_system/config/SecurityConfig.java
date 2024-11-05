package com.camperfire.library_management_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        // Define the query to retrieve a user by username
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "SELECT username, password, active FROM members WHERE username=?"
        );

        // Define the query to retrieve the authorities/roles by username
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT username, member_type FROM members WHERE username=?"
        );

        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers(HttpMethod.GET, "/api/books").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/books/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/authors").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/authors/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/**").hasAuthority("ADMIN")
                );

        http.httpBasic(Customizer.withDefaults());

        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
