package com.ig.amr.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ig.amr.config.security.CustomAuthenticationEntryPoint;
import com.ig.amr.config.security.JwtAuthenticationFilter;
import com.ig.amr.config.security.JwtProvider;
import com.ig.amr.service.AccountDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AppConfig {

    private final JwtProvider jwtProvider;
    private final AccountDetailsService accountDetailsService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        AuthenticationManager authManager = authManagerBuilder.build();

        http.authenticationManager(authManager);

        http
            .cors(withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers("/").permitAll()
                .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/v3/**").permitAll()
                .requestMatchers(
                    "/account/**"
                    , "/logistics/moveRout/**").permitAll()
                .requestMatchers("/static/**").permitAll()
                .requestMatchers(String.valueOf(HttpMethod.GET), "/common/images/**").permitAll()
                .requestMatchers("/ws", "/ws/*").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(new JwtAuthenticationFilter(jwtProvider, accountDetailsService),
                             UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}