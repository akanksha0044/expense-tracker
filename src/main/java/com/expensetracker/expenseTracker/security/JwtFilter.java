package com.expensetracker.expenseTracker.security;

import com.expensetracker.expenseTracker.service.CustomerDetailsService;
import com.expensetracker.expenseTracker.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    private final JwtService jwtService;
    private final CustomerDetailsService userDetailsService;

    public JwtFilter(JwtService jwtService, CustomerDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        log.info(">>> JwtFilter hit: {} {}", request.getMethod(), request.getServletPath());
        log.info(">>> Authorization header: {}", request.getHeader("Authorization") != null ? "PRESENT" : "MISSING");
        log.info(">>> Content-Type: {}", request.getContentType());

        // Skip login/register
        if (request.getServletPath().startsWith("/api/auth")) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);
                String username = jwtService.extractUsername(token);
                log.info(">>> Extracted username from token: {}", username);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (jwtService.validateToken(token, userDetails.getUsername())) {
                        log.info(">>> Token valid, setting authentication for: {}", username);

                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities()
                                );

                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    } else {
                        log.warn(">>> Token validation FAILED for username: {}", username);
                    }
                }
            } catch (Exception e) {
                log.error(">>> Token processing error: {}", e.getMessage(), e);
            }
        } else {
            log.warn(">>> No Bearer token found in request to: {}", request.getServletPath());
        }

        chain.doFilter(request, response);
    }
}

