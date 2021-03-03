package com.embl.person.config;

import com.embl.person.model.*;
import org.springframework.context.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.config.*;

import java.util.*;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditConfig {
    @Bean
    public AuditorAware<String> auditorAware() { return  ()-> Optional.of(User.getUser());}
}
