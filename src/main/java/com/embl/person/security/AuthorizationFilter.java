package com.embl.person.security;

import com.embl.person.util.*;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.*;
import org.springframework.security.web.authentication.www.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        String authorizationHeader = req.getHeader("Authorization");

        if (authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer")) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
        String authorizationHeader = req.getHeader("Authorization");

        if (authorizationHeader == null) {
            return null;
        }

        String token = authorizationHeader.replace("Bearer", "");

        String userName = Jwts.parser().setSigningKey("secret1234").parseClaimsJws(token)
                .getBody().getSubject();

        if (userName == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userName, null, new ArrayList<>());
    }
}
