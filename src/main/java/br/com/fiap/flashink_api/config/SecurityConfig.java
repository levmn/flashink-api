package br.com.fiap.flashink_api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private AuthFilter authFilter;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/transactions/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/users/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .build();
    }

    // @Bean
    // UserDetailsService userDetailsService() {
    //     return new InMemoryUserDetailsManager(List.of(
    //             User
    //                     .withUsername("allan")
    //                     .password("$2a$12$UOW2MMwt/cXWSUcRkvJguOeRUxxWX4fhZs.Cy6Minv9qI1LkXh2Pe")
    //                     .roles("ADMIN", "USER")
    //                     .build(),
    //             User
    //                     .withUsername("levi")
    //                     .password("$2a$12$kiudxYUEistkSyYTWaOEEedey//oJ.VqOr1UO9ysz22mbNlYJYT5q")
    //                     .roles("ADMIN", "USER")
    //                     .build()));
    // }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
