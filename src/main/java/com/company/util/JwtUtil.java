package com.company.util;

import com.company.enums.RoleType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class JwtUtil {

    private String SECRET_KEY = "bigsecret";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", convertRoles(userDetails.getAuthorities()));
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @SuppressWarnings("unchecked")
    public boolean userHasRole(final RoleType roleName) {
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals(roleName.name())) {
                return true;
            }
        }
        return false;
    }

    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();

    }

    private List<String> convertRoles(Collection<? extends GrantedAuthority> authorities) {
        List<String> authoritiesStrings = new ArrayList<>();
        System.out.println(authorities);
        authorities.forEach(a -> authoritiesStrings.add(a.getAuthority()));
        return authoritiesStrings;
    }
}
