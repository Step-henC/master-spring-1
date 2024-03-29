package com.exam.rest.webservices.restfulwebservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //annotation signifies multiple beans
public class SpringSecurityConfiguration { //THIS MUST BE IN ROOT APPLICATION PACKAGE TO WORK!!!!

    //customizing spring boot security filter chains
    //Spring security defaults preventing CSRF by forbidding POST and PUT methods
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests((auth) -> auth.anyRequest().authenticated()); //all request should be authenticated
        

         //if request is not authenticated, a webpage is shown
        //still not showing pop-up like it should
        http.httpBasic(withDefaults());
        http.csrf(c -> c.disable());
       

        return http.build();
    }

}


