package com.example.irctc.irctc_backend.config.Security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtHelper jwtHelper;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtHelper jwtHelper, UserDetailsService userDetailsService) {
        this.jwtHelper = jwtHelper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //har ek request se pahle
        // Bearer 34435325gfdgdgdfgfdgfdftrydtrtedrigetroijetroertporrowri
        String authHeadet =request.getHeader("Authorization");
        String username = null;
        String token = null;
        if(authHeadet!=null && authHeadet.startsWith("Bearer")){
            //Extraction the token from the header
            token=authHeadet.substring(7);// remove Bearer

            try{
                username = jwtHelper.getUsernameFromToken(token);
                if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if(jwtHelper.isTokenValid(token,userDetails)){
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=  new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        //security context me data add krna hai
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            }
            catch (IllegalArgumentException ex){
                System.out.println("unable to get jwt token");
                ex.printStackTrace();
            }catch (ExpiredJwtException ex){
                System.out.println("JWT token has expired");
                ex.printStackTrace();
            }catch (MalformedJwtException ex){
                logger.info("invalid token");
                ex.printStackTrace();

            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            // check if the toke is valid or not


        }else {
            System.out.println("Invalid authorization Header");
        }
        // ye request ko aage bhej dega
        filterChain.doFilter(request,response);
    }
}


