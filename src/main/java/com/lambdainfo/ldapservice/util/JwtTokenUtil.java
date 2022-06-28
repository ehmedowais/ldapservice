package com.lambdainfo.ldapservice.util;


import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWEDecryptionKeySelector;
import com.nimbusds.jose.proc.JWEKeySelector;
import com.nimbusds.jose.proc.SimpleSecurityContext;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;



@Component
public class JwtTokenUtil implements Serializable {


    private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 60 *60 *24;
    private static final String SIGNING_KEY = "";

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver)  {
        Claims claims = null;
        try {
            claims = getAllClaimsFromToken(token);
        } catch (BadJOSEException |ParseException|JOSEException e) {
            e.printStackTrace();
        }
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) throws BadJOSEException, ParseException, JOSEException {
      ConfigurableJWTProcessor<SimpleSecurityContext> jwtProcessor;
      jwtProcessor = jwtProcessor();
       var allClaims = JWTParser.parse(token).getJWTClaimsSet().getClaims();
        // var allClaims = PlainJWT.parse(token);
       return new DefaultClaims(allClaims);
    }
    public ConfigurableJWTProcessor<SimpleSecurityContext> jwtProcessor() {
        final String secretKey="helloworld";
        ConfigurableJWTProcessor<SimpleSecurityContext> jwtProcessor = new DefaultJWTProcessor<SimpleSecurityContext>();
        var jweKeySource = new ImmutableSecret<SimpleSecurityContext>(secretKey.getBytes(StandardCharsets.UTF_8));
        JWEKeySelector<SimpleSecurityContext> jweKeySelector =
                new JWEDecryptionKeySelector<SimpleSecurityContext>(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256, jweKeySource);
        jwtProcessor.setJWEKeySelector(jweKeySelector);
        return jwtProcessor;
    }
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }



    private String doGenerateToken(String subject) {

        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("http://devglan.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    public Boolean validateToken(String token) {
        final String username = getUsernameFromToken(token);
        return (!isTokenExpired(token));
    }

}
