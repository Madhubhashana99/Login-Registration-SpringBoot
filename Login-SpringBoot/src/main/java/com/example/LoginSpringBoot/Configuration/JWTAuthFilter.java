package com.example.LoginSpringBoot.Configuration;

import com.example.LoginSpringBoot.UserService.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader=request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader==null || !authHeader.startsWith("Bearer ")){ //Token Should be always starts with Bearer
            filterChain.doFilter(request,response);
            return;
        }
        jwt=authHeader.substring(7);//Extracting JWT Token
        userEmail=jwtService.extractUsername(jwt);//Extracting User Email Using JwtService class's extractUsername method
        if(userEmail !=null && SecurityContextHolder.getContext().getAuthentication()==null){ //Checking whether the userEmail is null and the User already Authenticated
            UserDetails userDetails=this.userDetailsService.loadUserByUsername(userEmail); //When not authenticated getting userEmail from userDetails
            if(jwtService.isTokenValid(jwt,userDetails)){ //Checking whether the User and Token is Valid.
                UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken( //Creating a object of UsernamePasswordAuthenticationToken and pass below values
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response); //Passing hand to next filter tobe executed
    }
}
