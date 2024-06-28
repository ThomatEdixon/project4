package com.aptech.bookingmovies.filters;

import com.aptech.bookingmovies.components.JwtTokenUtil;
import com.aptech.bookingmovies.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    @Value("${api.prefix}")
    private String apiPrefix;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try{
            if(isBypassToken(request)){
                filterChain.doFilter(request,response);
                return;
            }
            final String authHeader = request.getHeader("Authorization");
            if(authHeader == null && !authHeader.startsWith("Bearer ")){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
                return;
            }
            final String token = authHeader.substring(7);
            final String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);
            if(phoneNumber != null && SecurityContextHolder.getContext().getAuthentication()==null){
                User existingUser = (User) userDetailsService.loadUserByUsername(phoneNumber);
                if(jwtTokenUtil.validateToken(token,existingUser)){
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    existingUser,
                                    null,
                                    existingUser.getAuthorities()
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request,response);
        }catch (Exception e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
        }
    }
    private boolean isBypassToken(@NotNull HttpServletRequest request){
        final List<Pair<String,String>> bypassTokens = Arrays.asList(
                Pair.of(String.format("%s/product",apiPrefix),"GET"),
                Pair.of(String.format("%s/category",apiPrefix),"GET"),
                Pair.of(String.format("%s/user/login",apiPrefix),"POST"),
                Pair.of(String.format("%s/user/register",apiPrefix),"POST"),
                Pair.of(String.format("%s/movie/images",apiPrefix),"GET")
        );
        for(Pair<String,String> bypassToken:bypassTokens){
            if(request.getServletPath().contains(bypassToken.getFirst())&&
                    request.getMethod().equals(bypassToken.getSecond())){
                return true;
            }
        }
        return false;
    }
}
