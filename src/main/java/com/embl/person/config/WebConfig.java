package com.embl.person.config;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;

/*
As we used security context we need to authorize all the requests coming to this application
Enable frameoptions so that we can see the h2 console to connect to inmemory db
 */
@Configuration
@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/embl/**").permitAll().and().csrf().disable();
        httpSecurity.headers().frameOptions().sameOrigin();
    }
}
