package com.gfa.greenbay.services;

import io.jsonwebtoken.Claims;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
  String extractUsername(String token);

  <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

  String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);
  String generateToken(UserDetails userDetails);

  Boolean isTokenValid(String token, UserDetails userDetails);
}
