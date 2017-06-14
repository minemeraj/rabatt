package de.rwthaachen.webtech.rabatt.util;

import javax.servlet.ServletException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class JwtTokenParser {

  public Claims deserialize(String authHeader) throws ServletException {
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new ServletException("Missing or invalid Authorization header");
    }

    String token = authHeader.substring(7);

    try {
      return Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
    } catch (final SignatureException e) {
      throw new ServletException("Invalid token");
    }
  }
}
