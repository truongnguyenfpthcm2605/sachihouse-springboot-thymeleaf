package com.shachi.shachihouse.config;

import com.shachi.shachihouse.security.userprincal.UserDetailService;
import com.shachi.shachihouse.utils.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailService userDetailService;

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService);
        authenticationProvider.setPasswordEncoder(getPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).cors(cr -> cr.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.GET,"/health").permitAll();
                    auth.requestMatchers(HttpMethod.GET,"/index").permitAll();
                    auth.requestMatchers("/auth/**").permitAll();
                    auth.requestMatchers("/admin/**").hasAuthority(Roles.ADMIN.name());
                    auth.anyRequest().permitAll();
                })
                .formLogin(login -> login.loginPage("/auth/login/form")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/auth/login/success", true)
                        .failureUrl("/auth/login/error")
                        .usernameParameter("username")
                        .passwordParameter("password")
                )
                .rememberMe(remember -> remember.rememberMeParameter("remember")
                        .tokenValiditySeconds(86400)
                )
                .logout(httpSecurityLogoutConfigurer ->
                        httpSecurityLogoutConfigurer.invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .clearAuthentication(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                                .logoutSuccessUrl("/auth/logout/success")
                                .addLogoutHandler(new SecurityContextLogoutHandler())
                )

                .exceptionHandling(ex -> ex.accessDeniedPage("/auth/denied"))
                .authenticationProvider(authenticationProvider());
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setExposedHeaders(Collections.singletonList("Authorization"));
        configuration.addAllowedOrigin("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }



}