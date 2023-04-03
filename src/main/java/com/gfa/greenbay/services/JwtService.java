package com.gfa.greenbay.services;

import io.jsonwebtoken.Claims;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

  String extractUsername(String token);

  <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

  String generateToken(UserDetails userDetails, Map<String, Object> extraClaims);

  String generateToken(UserDetails userDetails);

  Boolean isTokenValidForUsername(String token, UserDetails userDetails);
}
