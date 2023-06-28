package com.yu;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Map;

//@SpringBootTest
public class JWTTest {

    @Test
    public void Jwt() throws InterruptedException {
        testGenJWT();
        testParseJWT();
    }
    @Test
    public void testGenJWT(){
//        String compact = jwt.builder()
//                .signWith(SignatureAlgorithm.HS256, "AnYu")
//                .setClaims(Map.of("username", "xiaoming", "role", "admin"))
//                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
//                .compact();
        token = JWT.create()
                .withClaim("id", "1")
                .withClaim("name", "tom")
                .withExpiresAt(new Date(System.currentTimeMillis()))
                .sign(Algorithm.HMAC256("AnYu"));
        System.out.println(token);
    }

    String token;
    @Test
    public void testParseJWT(){
        try {
            Map<String, Claim> claims = JWT.require(Algorithm.HMAC256("AnYu"))
                    .build()
                    .verify(token)
                    .getClaims();
            System.out.println(claims);
        }catch (SignatureVerificationException e){
            System.out.println("token被篡改");
        }catch (TokenExpiredException e){
            System.out.println("token过期");
        }
    }



}
