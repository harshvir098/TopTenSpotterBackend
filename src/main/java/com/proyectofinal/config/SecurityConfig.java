package com.proyectofinal.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Value("${app.local-domain-front}")
    private String localDomainFront;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthenticationSuccessHandler succesHandler() {
        return (request, response, authentication) -> response.sendRedirect("/");
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder)
            throws Exception {
        AuthenticationManagerBuilder authenticationManager = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManager.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManager.build();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())// Deshabilitamos la protección contra ataques Cross-site request forgery
                .cors(withDefaults())
                .authorizeHttpRequests((requests) -> {
                    try {
                        // Definimos que urls estarán desprotegidas y no necesitarán recibir las credenciales para poder ser accedidas
                        requests .requestMatchers("/api/autonomies").permitAll()  // Allow access to the autonomies endpoint
                        .requestMatchers("/auth/register").permitAll() 
                        .anyRequest().authenticated();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).httpBasic(withDefaults());
        return http.build();
    }

    // Configuración del CORS (Cross-origin resource sharing)
    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(localDomainFront);
                registry.addMapping("/**").allowedMethods("POST", "PUT", "GET", "DELETE", "OPTIONS");
            }
        };
    }
}
