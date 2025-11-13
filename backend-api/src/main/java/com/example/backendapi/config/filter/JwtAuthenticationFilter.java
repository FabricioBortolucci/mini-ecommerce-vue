package com.example.backendapi.config.filter;

import com.example.backendapi.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userName;

        // Verifica se o cabeçalho existe e se começa com "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Se não, passa para o próximo filtro e sai
            return;
        }

        //  Extrai o token, remove o Bearer
        jwt = authHeader.substring(7);

        //  Extrai o username de dentro do token
        userName = jwtService.extractUsername(jwt);

        //  Verifica se o username existe E se o usuário ainda não está autenticado
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            //  Busca o usuário no banco de dados (usando o UserDetailsService)
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);

            //  Se o token for válido...
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // 8. Cria o "ticket" de autenticação para o Spring
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // "Autentica" o usuário para esta requisição
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Passa a requisição para o próximo filtro
        filterChain.doFilter(request, response);
    }
}
