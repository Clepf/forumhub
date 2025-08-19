package com.forumhub.api.config;

import com.forumhub.api.security.JwtAuthenticationFilter;
import com.forumhub.api.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final AuthService authService;
    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(AuthService authService, JwtAuthenticationFilter jwtFilter) {
        this.authService = authService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Expõe AuthenticationManager para ser usado no AuthController
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Define as regras de segurança e inclui o filtro JWT
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // libera login e criação de usuário sem token
                        .requestMatchers("/login", "/usuarios").permitAll()
                        // exige autenticação para todos os endpoints /topicos/**
                        .requestMatchers("/topicos/**").authenticated()
                        // demais rotas também exigem token
                        .anyRequest().authenticated()
                )
                // registra o seu UserDetailsService
                .userDetailsService(authService)
                // adiciona o filtro JWT antes do filtro padrão de username/password
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                // fallback HTTP Basic (opcional)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
