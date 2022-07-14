package com.sistema.blog.sistemablogspringbootapirest.seguridad;


import com.sistema.blog.sistemablogspringbootapirest.exceptions.BlogAPPExceptions;
import com.sistema.blog.sistemablogspringbootapirest.exceptions.ResourceNotFoundException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

        @Value("${app.jwt-expiration-milliseconds}")
        private int jwtExpirationInMs;

        public String generarToken(Authentication authentication) {
            String username = authentication.getName();
            Date fechaActual = new Date();
            Date fechaExpiracion = new Date(fechaActual.getTime() + jwtExpirationInMs);

            String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(fechaExpiracion)
                    .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

            return token;
        }

        public String obtenerUsernameDelJWT(String token) {
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return claims.getSubject();
        }

        public boolean validarToken(String token) {
            try {
                Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
                return true;
            }catch (SignatureException ex) {
                throw new BlogAPPExceptions(HttpStatus.BAD_REQUEST,"Firma JWT no valida", ex.getMessage(), new Date());
            }
            catch (MalformedJwtException ex) {
                throw new BlogAPPExceptions(HttpStatus.BAD_REQUEST,"Token JWT no valida", ex.getMessage(), new Date());
            }
            catch (ExpiredJwtException ex) {
                throw new BlogAPPExceptions(HttpStatus.BAD_REQUEST,"Token JWT caducado", ex.getMessage(), new Date());
            }
            catch (UnsupportedJwtException ex) {
                throw new BlogAPPExceptions(HttpStatus.BAD_REQUEST,"Token JWT no compatible", ex.getMessage(), new Date());
            }
            catch (IllegalArgumentException ex) {
                throw new BlogAPPExceptions(HttpStatus.BAD_REQUEST,"La cadena claims JWT esta vacia", ex.getMessage(), new Date());
            }
        }
}

