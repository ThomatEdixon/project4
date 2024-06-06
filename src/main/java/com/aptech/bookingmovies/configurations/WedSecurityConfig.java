package com.aptech.bookingmovies.configurations;

import com.aptech.bookingmovies.filters.JwtTokenFilter;
import com.aptech.bookingmovies.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WedSecurityConfig {
    @Value("${api.prefix}")
    private String apiPrefix;
    private final JwtTokenFilter jwtTokenFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests->{
                    requests.requestMatchers(
                                    String.format("%s/user/login",apiPrefix),
                                    String.format("%s/user/register",apiPrefix)
                            )
                            .permitAll()
                            // api cinema
                            .requestMatchers(HttpMethod.POST,
                                    String.format("%s/cinema/createCinema",apiPrefix)
                            ).hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.PUT,
                                    String.format("%s/cinema/updateCinema",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA)
                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/cinema/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA,Role.USER)
                            .requestMatchers(HttpMethod.DELETE,
                                    String.format("%s/cinema/**",apiPrefix)
                            ).hasRole(Role.ADMIN)

                            //api room
                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/room/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA,Role.USER)
                            .requestMatchers(HttpMethod.POST,
                                    String.format("%s/cinema/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA)
                            .requestMatchers(HttpMethod.PUT,
                                    String.format("%s/cinema/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA)
                            .requestMatchers(HttpMethod.DELETE,
                                    String.format("%s/cinema/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA)

                            //api seat
                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/seat/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA,Role.USER)
                            .requestMatchers(HttpMethod.POST,
                                    String.format("%s/seat/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA)
                            .requestMatchers(HttpMethod.PUT,
                                    String.format("%s/seat/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA)
                            .requestMatchers(HttpMethod.DELETE,
                                    String.format("%s/seat/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA)

                            //api movie
                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/movie/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA,Role.USER)
                            .requestMatchers(HttpMethod.POST,
                                    String.format("%s/movie/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA)
                            .requestMatchers(HttpMethod.PUT,
                                    String.format("%s/movie/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA)
                            .requestMatchers(HttpMethod.DELETE,
                                    String.format("%s/movie/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA)

                            //api schedule
                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/schedule/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA,Role.USER)
                            .requestMatchers(HttpMethod.POST,
                                    String.format("%s/schedule/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA)
                            .requestMatchers(HttpMethod.PUT,
                                    String.format("%s/schedule/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA)
                            .requestMatchers(HttpMethod.DELETE,
                                    String.format("%s/schedule/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA)

                            //api ticket
                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/ticket/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA,Role.USER)
                            .requestMatchers(HttpMethod.POST,
                                    String.format("%s/ticket/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA)
                            .requestMatchers(HttpMethod.PUT,
                                    String.format("%s/ticket/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA)
                            .requestMatchers(HttpMethod.DELETE,
                                    String.format("%s/ticket/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA)

                            //api promotion
                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/promotion/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA)
                            .requestMatchers(HttpMethod.POST,
                                    String.format("%s/promotion/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA)
                            .requestMatchers(HttpMethod.PUT,
                                    String.format("%s/promotion/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA)
                            .requestMatchers(HttpMethod.DELETE,
                                    String.format("%s/promotion/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA)

                            //api bill
                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/bill/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA,Role.USER)
                            .requestMatchers(HttpMethod.POST,
                                    String.format("%s/bill/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA,Role.USER)
                            .requestMatchers(HttpMethod.PUT,
                                    String.format("%s/bill/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA,Role.USER)
                            .requestMatchers(HttpMethod.DELETE,
                                    String.format("%s/bill/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA,Role.USER)

                            //api bill_ticket
                            .requestMatchers(HttpMethod.GET,
                                    String.format("%s/bill_ticket/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA,Role.USER)
                            .requestMatchers(HttpMethod.POST,
                                    String.format("%s/bill_ticket/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA,Role.USER)
                            .requestMatchers(HttpMethod.PUT,
                                    String.format("%s/bill_ticket/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA,Role.USER)
                            .requestMatchers(HttpMethod.DELETE,
                                    String.format("%s/bill_ticket/**",apiPrefix)
                            ).hasAnyRole(Role.ADMIN,Role.CINEMA,Role.USER)

                            .anyRequest()
                            .authenticated();
                })
                .build();
    }
}
