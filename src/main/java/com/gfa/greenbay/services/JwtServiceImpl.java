package com.gfa.greenbay.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

  private final String SECRET_KEY;
  private final JwtParser jwtParser;

  public JwtServiceImpl(@Value("${JWT_SECRET_KEY}") String SECRET_KEY) {
    this.SECRET_KEY = SECRET_KEY;
    this.jwtParser = Jwts.parserBuilder().setSigningKey(getSigningKey()).build();
  }

  @Override
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  @Override
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  @Override
  public String generateToken(UserDetails userDetails, Map<String, Object> extraClaims) {
    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) // 24H
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  @Override
  public String generateToken(UserDetails userDetails) {
    return generateToken(userDetails, new HashMap<>());
  }

  @Override
  public Boolean isTokenValidForUsername(String token, String givenUsername) {
    return (extractUsername(token).equals(givenUsername)) && !isTokenExpired(token);
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return jwtParser.parseClaimsJws(token).getBody();
  }

  private Key getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
