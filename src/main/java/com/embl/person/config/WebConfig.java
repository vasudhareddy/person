package com.embl.person.config;

import com.embl.person.security.*;
import com.embl.person.util.*;
import com.fasterxml.jackson.databind.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.*;

/*
As we used security context we need to authorize all the requests coming to this application
Enable frameoptions so that we can see the h2 console to connect to inmemory db
 */
@Configuration
@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JSONUtil jsonUtil;

    @Autowired
    ObjectMapper mapper;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.authorizeRequests().antMatchers("/persons/**")
                .authenticated()
                .and()
                .addFilter(new AuthorizationFilter(authenticationManager()));
        httpSecurity.addFilter(getAuthenticationFilter())
                .authorizeRequests().antMatchers("/persons/authenticate/**").permitAll();

        httpSecurity.headers().frameOptions().sameOrigin();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(userDetailsService, jsonUtil, mapper);
        authenticationFilter.setAuthenticationManager(authenticationManager());
        authenticationFilter.setFilterProcessesUrl("/persons/authenticate");
        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}
