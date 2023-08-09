package com.person.SpringSecurityJWTDemo001.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;


/* All the service methods present in this class which help to perform JWT based validation */

@Component
public class JWTService {
    private final String SECRET = "196f4b947dd38dfbc908068a2b756269dde9468f1975399e130752bf2cc6ad10";

    /**
     * this will give you the Key for the Encryption in the token
     */

    private Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    /**
     * this will give us the actual token we use in our program to access the endpoints
     */
    public String generateToken(String username){
        Map<String, Objects> claims = new HashMap<>();
        return createToken(claims,username);
    }

    /**
     * We can say, this method is the method which help to build the token
     * by some of the setter methods and signWith() which give information
     * about signature algorithm.
     *
     * Here one setter methods sets the header which contains the username related details,
     * another set claims. Similarly, others set :
     *      - Issued time
     *      - Exparied time etc.
     */

    private String createToken(Map<String, Objects> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()*1000*60*30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * The claims in a JWT are encoded as a JSON object that is used as
     * the payload of a JSON Web Signature (JWS) structure or
     * as the plain-text of a JSON Web Encryption (JWE) structure.
     */

    /**
     *  This method help to extract claims from the token
     */

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build().parseClaimsJws(token).getBody();
    }

    /**
     * This is the helper method that help the extract particular contain from the claim
     */

    private <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Rest of the methods are pretty much self-explanatory
     */

    public String extractUsername(String token){
        return extractClaims(token,Claims::getSubject);
    }

    private Date extractExpiration(String token){
        return extractClaims(token,Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}
