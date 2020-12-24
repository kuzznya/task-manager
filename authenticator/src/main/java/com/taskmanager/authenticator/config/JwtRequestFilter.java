package com.taskmanager.authenticator.config;

import com.taskmanager.authenticator.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final WorkerService workerService;
    private final JwtTokenUtil tokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestTokenHeader = request.getHeader("Authorization");

        if (requestTokenHeader == null ||
                !requestTokenHeader.startsWith("Bearer ") ||
                SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = requestTokenHeader.substring(7);
        String username;
        try {
            username = tokenUtil.getUsernameFromToken(jwt);
        } catch (Exception ex) {
            filterChain.doFilter(request, response);
            return;
        }

        workerService.findWorkerByUsername(username).ifPresent(user -> {
            if (!request.getRequestURI().equals("/authenticator/authentication/refresh") &&
                    tokenUtil.validateToken(jwt, user.toUser())) {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        user, jwt, user.toUser().getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(token);
            }
            // validate refresh token if request is POST /authentication with refresh token
            else if (request.getRequestURI().equals("/authenticator/authentication/refresh") &&
                    request.getMethod().equals(HttpMethod.POST.name()) &&
                    tokenUtil.validateRefreshToken(jwt, user.toUser())) {
                List<GrantedAuthority> authorities = new ArrayList<>(user.toUser().getAuthorities());
                authorities.add(new SimpleGrantedAuthority("REFRESH"));
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        user, jwt, authorities);
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        });

        filterChain.doFilter(request, response);
    }
}
