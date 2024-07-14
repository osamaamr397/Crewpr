package com.example.Crewpr.security;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    public static final String ADMIN = "admin";
    public static final String USER = "user";

    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.
                authorizeHttpRequests(auth ->
                {
                    auth.requestMatchers(HttpMethod.GET, "/test/getEmployeefromKeycloak").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/test/SubmitVacation").hasRole(ADMIN);
                    auth.requestMatchers(HttpMethod.GET, "/test/Total-balance/{empId}").hasRole(ADMIN);
                    auth.requestMatchers(HttpMethod.GET, "/test/vacation-history/{empId}").hasRole(ADMIN);
                    auth.requestMatchers(HttpMethod.GET, "/test/Total-remain/{empId}").hasAnyRole(ADMIN);
                    auth.anyRequest().authenticated();
                });

        http.
                oauth2ResourceServer(oauth2 -> oauth2.jwt(
                        jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)
                ));

        http.
                sessionManagement((session) ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}