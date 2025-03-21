package com.ps.employeepayroll.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.ps.employeepayroll.repository.EmployeeRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http, EmployeeRepository employeeRepository)
                        throws Exception {
                http
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                                .requestMatchers("/user/**").hasRole("USER")
                                                .requestMatchers("/", "/auth/**", "/oauth2/**").permitAll()
                                                .anyRequest().authenticated())
                                .oauth2Login(oauth2 -> oauth2
                                                .loginPage("/auth/login")
                                                .successHandler(new OAuth2LoginSuccessHandler(employeeRepository)) // Pass
                                                                                                                   // the
                                                                                                                   // repo
                                                                                                                   // here
                                )
                                .logout(logout -> logout.logoutUrl("/auth/logout")
                                                .logoutSuccessUrl("/auth/login?logout"));

                return http.build();
        }

        // ✅ Password Encoding
        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        // ✅ Custom Success Handler to Redirect Based on Role
        @Bean
        public AuthenticationSuccessHandler successHandler() {
                return (request, response, authentication) -> {
                        // Check user roles and redirect accordingly
                        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                                response.sendRedirect("/admin/dashboard");
                        } else {
                                response.sendRedirect("/user/dashboard");
                        }
                };
        }

        // Removed the configure(HttpSecurity) method as it is not valid in this
        // context.

}
