package com.sonng2k.cashcard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/*
 This will be the Java configuration Bean for Spring Security in our app.
 The @Configuration annotation tells Spring to use this class to configure Spring and Spring Boot itself.
 Any Beans specified in this class will now be available to Spring's Auto Configuration engine.
*/
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /*
     Spring Security expects a Bean to configure its Filter Chain. Annotating a method returning a
     SecurityFilterChain with the @Bean satisfies this expectation.

     Spring Security's builder pattern: All HTTP requests to cashcards/ endpoints are required to be
     authenticated using HTTP Basic Authentication security (username and password). Also, do not require CSRF
     security.
    */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        // The method reference below can be replaced with this Lambda: csrf -> csrf.disable()
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/cashcards/**")
                        // enable RBAC: Replace the .authenticated() code with this line.
                        // restrict access to only users with the CARD-OWNER role.
                        .hasRole("CARD-OWNER")
                )
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configure an in-memory test-only UserDetailsService with test users to test Spring Security.
    // Spring's IoC container will find the UserDetailsService Bean and Spring Data will use it when needed.
    @Bean
    public UserDetailsService testOnlyUsers(PasswordEncoder passwordEncoder) {
        User.UserBuilder users = User.builder();
        UserDetails sarah = users
                .username("sarah1")
                .password(passwordEncoder.encode("abc123"))
                .roles("CARD-OWNER")
                .build();
        UserDetails hankOwnsNoCards = users
                .username("hank-owns-no-cards")
                .password(passwordEncoder.encode("qrs456"))
                .roles("NON-OWNER")
                .build();
        UserDetails kumar = users
                .username("kumar2")
                .password(passwordEncoder.encode("xyz789"))
                .roles("CARD-OWNER")
                .build();
        return new InMemoryUserDetailsManager(sarah, hankOwnsNoCards, kumar);
    }
}
