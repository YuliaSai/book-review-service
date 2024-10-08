package com.book.review.service.config;

import com.book.review.service.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private static final String BASE_BOOKS_URL = "/api/v1/books";
    private static final String BASE_REVIEWS_URL = "/api/v1/reviews";
    private static final String BASE_AUTH_URL = "/api/v1/auth";
    private static final String ACTUATOR_URI = "/actuator/**";
    private static final String[] SWAGGER_URL = { "/book-review-service/v1/api-docs/**", "/swagger",
            "/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequestsConfigurer ->
                        authorizeRequestsConfigurer
                                .requestMatchers(ACTUATOR_URI)
                                .permitAll()
                                .requestMatchers(SWAGGER_URL)
                                .permitAll()
                                .requestMatchers(HttpMethod.GET, BASE_BOOKS_URL, BASE_BOOKS_URL + "/**",
                                        BASE_REVIEWS_URL, BASE_REVIEWS_URL + "/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.POST, BASE_AUTH_URL + "/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.POST, BASE_REVIEWS_URL)
                                .authenticated()
                                .requestMatchers(HttpMethod.PUT, BASE_REVIEWS_URL + "/**")
                                .authenticated()
                                .requestMatchers(HttpMethod.DELETE, BASE_REVIEWS_URL + "/**")
                                .authenticated()
                                .anyRequest().denyAll())
                .formLogin(formLoginConfigurer ->
                        formLoginConfigurer
                                .loginPage("/login")
                                .permitAll())
                .exceptionHandling(exceptionHandlingConfigurer ->
                        exceptionHandlingConfigurer
                                .accessDeniedHandler((request, response, accessDeniedException) ->
                                        response.sendError(HttpServletResponse.SC_FORBIDDEN))
                                .authenticationEntryPoint((request, response, authException) ->
                                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED)))
                .logout(logoutConfigurer ->
                        logoutConfigurer
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout")
                                .permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOriginPattern("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authProvider())
                .build();
    }

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}