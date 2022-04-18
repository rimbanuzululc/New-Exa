package com.bsi.exa.apiserver.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.starlight.Component;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author denny
 */
@Component
public class TokenService {
    
    protected Key key = null;
    
    protected synchronized Key getKey() {
        
        if (key == null)
            key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        
        return key;
    }
    
    public String generateToken(String userId) {
        
        int expireInterval = 60;
        Date expDate = new Date(new Date().getTime() + (expireInterval * 60 * 1000));
                
        return Jwts
                    .builder()
                    .setSubject(userId)
                    .setId(UUID.randomUUID().toString())
                    .setIssuedAt(new Date())
                    .setExpiration(expDate)
                    .signWith(getKey())
                    .compact();
    }
    
    public Jws<Claims> validate(String token) throws Exception {
        
        return Jwts
                    .parser()
                    .setSigningKey(getKey())
                    .parseClaimsJws(token);
    }
}
