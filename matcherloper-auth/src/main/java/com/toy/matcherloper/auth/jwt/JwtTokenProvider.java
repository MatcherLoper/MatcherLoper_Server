package com.toy.matcherloper.auth.jwt;

import com.toy.matcherloper.auth.security.model.UserPrincipal;
import com.toy.matcherloper.auth.security.service.CustomUserDetailsService;
import com.toy.matcherloper.config.AuthProperties;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    private final AuthProperties properties;
    private final CustomUserDetailsService customUserDetailsService;

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        //System.out.println("tokenSecret = " + properties.getAuth().getTokenSecret());

        Date nowDate = new Date();
        Date expiryDate = new Date(nowDate.getTime() + properties.getAuth().getTokenExpirationMsec());

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(nowDate)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, properties.getAuth().getTokenSecret())
                .compact();
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        UserDetails userDetails = customUserDetailsService.loadUserById(getUserIdFromToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(properties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                    .setSigningKey(properties.getAuth().getTokenSecret())
                    .parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("유효하지 않은 JWT 서명");
        } catch (MalformedJwtException e) {
            log.error("유효하지 않은 JWT 토큰");
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰");
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 JWT");
        } catch (IllegalArgumentException e) {
            log.error("비어있는 JWT");
        }

        return false;
    }
}
