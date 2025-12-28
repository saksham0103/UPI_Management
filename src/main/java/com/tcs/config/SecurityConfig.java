package com.tcs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // ✅ REQUIRED for Option 2 (WebMvcConfigurer)
            .cors(Customizer.withDefaults())

            // 🔒 Stateless API
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // ❌ Disable default auth mechanisms
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())

            // 🔐 Authorization rules
            .authorizeHttpRequests(auth -> auth

                // 🔓 Public auth endpoints
                .requestMatchers("/api/auth/**").permitAll()

                // 🔓 Swagger endpoints
                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/swagger-ui/index.html",
                    "/v3/api-docs/**"
                ).permitAll()

                // 🔐 Protected APIs
                .requestMatchers("/api/admin/**").authenticated()
                .requestMatchers("/api/user/**").authenticated()

                .anyRequest().authenticated()
            )

            // 🔑 JWT Filter
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}
