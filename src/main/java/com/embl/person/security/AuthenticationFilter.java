package com.embl.person.security;

import com.embl.person.model.*;
import com.embl.person.service.*;
import com.embl.person.util.*;
import com.fasterxml.jackson.databind.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.*;
import org.springframework.stereotype.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    UserDetailsService userDetailsService;
    JSONUtil jsonUtil;
    ObjectMapper mapper;

    public AuthenticationFilter(UserDetailsService userDetailsService, JSONUtil jsonUtil, ObjectMapper mapper) {
        this.userDetailsService = userDetailsService;
        this.jsonUtil = jsonUtil;
        this.mapper = mapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {

            UserRequest creds = mapper.readValue(req.getInputStream(), UserRequest.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getName(),
                            creds.getPassword(),
                            new ArrayList<>())
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        String userName = ((CustomUserDetails) auth.getPrincipal()).getUsername();
        String token = jsonUtil.generateToken(userDetailsService.loadUserByUsername(userName));
        res.addHeader("token", token);
    }


}
