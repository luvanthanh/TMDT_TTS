package com.tmdttts.backend.config;

import com.tmdttts.backend.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Bean SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {
        http.csrf(csrf->csrf.disable()).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/auth/**","/error").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/categories/**","/api/products/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/contact").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/payments/vnpay/ipn").permitAll().anyRequest().authenticated())
                .httpBasic(basic->basic.disable()).formLogin(form->form.disable())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
}
