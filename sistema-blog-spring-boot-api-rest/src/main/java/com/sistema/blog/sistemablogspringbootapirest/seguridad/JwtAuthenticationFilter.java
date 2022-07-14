package com.sistema.blog.sistemablogspringbootapirest.seguridad;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema.blog.sistemablogspringbootapirest.dto.RespuestaError;
import com.sistema.blog.sistemablogspringbootapirest.exceptions.BlogAPPExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JwtAuthenticationFilter  extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            //obtenemos el token de la solicitud HTTP
            String token = obtenerJWTdeLaSolicitud(request);

            //validamos el token
            if(StringUtils.hasText(token) && jwtTokenProvider.validarToken(token)) {
                //obtenemos el username del token
                String username = jwtTokenProvider.obtenerUsernameDelJWT(token);

                //cargamos el usuario asociado al token
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //establecemos la seguridad
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        } catch (BlogAPPExceptions e) {

            // custom error response class used across my project
            RespuestaError errorResponse = new RespuestaError(e.getFecha(), e.getMensaje(), e.getDetalle());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(e.getEstado().value());
            response.getWriter().write(convertObjectToJson(errorResponse));
        }

    }
    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
    //Bearer token de acceso
    private String obtenerJWTdeLaSolicitud(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7,bearerToken.length());
        }
        return null;
    }
}
