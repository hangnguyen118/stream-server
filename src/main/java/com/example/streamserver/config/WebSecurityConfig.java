package com.example.streamserver.config;

import com.example.streamserver.service.CustomUserDetailsService;
import com.example.streamserver.service.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private static final String[] WHITE_LIST_URL = {
            "/api/auth/**",
            "/api/stream/**"
    };

    private static final String[] USER_LIST_URL = {
            "/api/user/**",
    };
    private static final String[] ADMIN_LIST_URL = {
            "/api/admin/**",
    };

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService customUserDetailsService) {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        List<AuthenticationProvider> providers =  List.of(authProvider);

        return new ProviderManager(providers);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf->csrf.disable())
                .cors(cors->cors.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("http://localhost", "http://localhost:63342", "http://192.168.1.76", "http://10.13.129.31"));
                    configuration.setAllowedMethods(List.of("HEAD","GET","POST","PUT","DELETE","PATCH","OPTIONS"));
                    configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                    configuration.setAllowCredentials(true);
                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", configuration);
                    return configuration;

                }))
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers(WHITE_LIST_URL).permitAll()
                                .requestMatchers(USER_LIST_URL).hasRole("USER")
                                .requestMatchers(ADMIN_LIST_URL).hasRole("ADMIN")
                                .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout((logout) ->
                        logout.logoutUrl("/api/auth/logout").permitAll().invalidateHttpSession(true).deleteCookies("accessToken").logoutSuccessUrl("/api/auth/userLogout")
                );
        return http.build();
    }
}