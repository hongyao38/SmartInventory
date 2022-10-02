package com.smartinventory.security.config;

import static java.util.Arrays.stream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {

    private static final String SECRET = "secret";
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());
    private static final JWTVerifier verifier = JWT.require(algorithm).build();
    
    /*
     * Takes in UserDetails object and returns a JWT string
     * associated with said user
     */
    public static String createJWT(UserDetails userDetails) {
        String accessToken = JWT.create()
                            .withSubject(userDetails.getUsername())
                            .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                            .withIssuer("localhost:8080/api/v1/login")
                            .withClaim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                            .sign(algorithm);
        return accessToken;
    }


    /*
     * Takes in a JWT string and extract Username
     */
    public static String getUsername(String accessToken) {
        DecodedJWT decodedJWT = verifier.verify(accessToken);
        return decodedJWT.getSubject();
    }


    /*
     * Takes in a JWT string and extract authorities
     */
    public static Collection<SimpleGrantedAuthority> getAuthorities(String accessToken) {
        DecodedJWT decodedJWT = verifier.verify(accessToken);

        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role));
        });
        return authorities;
    }
}
