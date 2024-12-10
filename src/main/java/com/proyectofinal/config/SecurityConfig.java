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
        http.csrf(csrf -> csrf.disable())  // Disable Cross-Site Request Forgery protection
                .cors(withDefaults())  // Enable Cross-Origin Resource Sharing (CORS)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/autonomies", "/api/autonomies/{name}", "api/places/{placeName}").permitAll()  // Allow access to the autonomies endpoint without authentication
                        .requestMatchers("/auth/register").permitAll()
                        .requestMatchers("/api/ratings/**").authenticated()
                        .requestMatchers("/api/autonomy/{autonomyId}/category/{category}","/images/**", "/api/comments/place/{placeId}").permitAll() // Allow access to the register endpoint without authentication
                        .anyRequest().authenticated()  // All other requests require authentication
                )
                .httpBasic(withDefaults())  // Enable HTTP Basic Authentication
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")  // URL to trigger logout
                        .logoutSuccessUrl("/auth/login")  // Redirect after logout
                        .invalidateHttpSession(true)  // Invalidate the session
                        .deleteCookies("JSESSIONID")  // Delete session cookie
                );
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