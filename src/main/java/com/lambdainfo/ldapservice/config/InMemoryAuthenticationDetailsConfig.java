package com.lambdainfo.ldapservice.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.client.RestTemplate;

@Configuration
public class InMemoryAuthenticationDetailsConfig {

    @Bean
    public UserDetailsService users() {
        String password = "{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW";
        UserDetails user = User.builder().username("user")
                .password(password).roles(new String[]{"user"}).build();

        UserDetails admin = User.builder().username("admin")
                .password(password).roles(new String[]{"admin"}).build();

        return new InMemoryUserDetailsManager(user, admin);

    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
