package miu.edu.etitle.security;

import miu.edu.etitle.config.JPAUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JPAUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        // case 1: User have
        String authHeader = httpServletRequest.getHeader("Authorization");
        if (authHeader != null) {
            // Do validate token
            String token = authHeader.substring(7);
            String username = jwtUtil.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            boolean tokenValid = jwtUtil.validateToken(token, userDetails);
            if(tokenValid) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        // case 1: User don't have token
        // allow user to go to next filter chain
        // go to /authentication to obtain token
        // Don't worry, when you user don't have token but allow to go next filter, we already added specific
        // security at controller and method level

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
