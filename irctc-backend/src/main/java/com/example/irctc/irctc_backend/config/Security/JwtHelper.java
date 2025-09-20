package com.example.irctc.irctc_backend.config.Security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.PrivateKey;
import java.util.Date;

@Component
public class JwtHelper {


    private  static  final  long JWT_VALIDITY = 5*60*1000;// 5 minutes

    private final  String SECRET = "dsadsdsewrrewredfffdgfdgftdhtrytryetrtrgfgbfghgytryytrerrvfdgtryrterfgfgryrfgvfdgfdgdfgfdgfdgfgghhgjfhgfhfgh";

    private Key key;


    @PostConstruct
    public void  init(){
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    //generate toke

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((System.currentTimeMillis() + JWT_VALIDITY)))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }



    // get username from token
    public String getUsernameFromToken(String token){
        return  getClaims(token).getSubject();
    }

    // get all claims from token
    private Claims getClaims(String token){
        return  Jwts.parserBuilder()
                .setSigningKey(key)
                .build().parseClaimsJws(token)
                .getBody();
    }


    // validate token

    public  boolean isTokenValid(String token, UserDetails userDetails){
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenValid(token);
    }


    // check token expiration

    private   boolean isTokenValid(String token){
        return  getClaims(token).getExpiration().before(new Date());
    }


// refresh token


}
