package com.filmupia.backend.config.auth;

import com.filmupia.backend.service.auth.filter.JwtAuthenticationFilter;
import com.filmupia.backend.service.auth.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            UserDetailsServiceImpl userDetailsServiceImpl
    ) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                "/api/v1/auths/register", "/api/v1/auths/login",
                                "/api/v1/authors/**", "/api/v1/books/**",
                                "api/v1/genres/**", "/api/v1/publishers/**",
                                "/api/v1/quotes/**", "/api/v1/blogs/**",
                                "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**",
                                "/api/v1/blogs/**"
                        ).permitAll()
                        .requestMatchers(
                                "/api/v1/users/**", "/api/v1/readBooks/**",
                                "/api/v1/toReadBooks**", "/api/v1/comments/**",
                                "/api/v1/blogs/user"
                        ).hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/api/v1/authors/admin", "/api/v1/books/admin",
                                "api/v1/genres/admin", "/api/v1/publishers/admin",
                                "/api/v1/quotes/admin", "api/v1/emails/**",
                                "/api/v1/auths/admin/**", "/api/v1/users/admin/**")
                        .hasAnyAuthority("ADMIN")
                        .anyRequest()
                        .authenticated()
                ).userDetailsService(userDetailsServiceImpl)
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}