package com.example.ApiDemo.services;

import com.example.ApiDemo.models.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRET_KEY = "19521a858030d31106e4b271f2b437f94c5f7758510935ca42c4df6961fdc248";

    public boolean isValid( String token, UserDetails user ) {
        String username = extractUsername( token );
        //recordar que mi Users implements UserDetails user.getUsername() regresa el email
        return (username.equals( user.getUsername() )) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim( token, Claims::getExpiration );
    }

    public String extractUsername( String token ) {
        return extractClaim( token, Claims::getSubject );
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver ) {
        Claims claims = extractAllClaims( token );

        return resolver.apply( claims );
    }

    private Claims extractAllClaims( String token ) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims( token )
                .getPayload();
    }

    public String generateToken(Users user ) {
        String token = Jwts
                .builder()
                //En el método getUsername yo puse que regresara un email el suyo siguen en null
                .subject( user.getUsername() )
                .issuedAt( new Date( System.currentTimeMillis() ))
                .expiration( new Date( System.currentTimeMillis() + 24*60+60*1000 ) )
                .signWith( getSigningKey() )
                .compact();

        return token;
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode( SECRET_KEY );
        return Keys.hmacShaKeyFor( keyBytes );
    }

}
