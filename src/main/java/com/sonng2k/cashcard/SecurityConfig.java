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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                http.csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(auth -> auth.requestMatchers("/cashcards/**")
                                                .hasRole("CARD-OWNER"))
                                .httpBasic(withDefaults());
                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        // Configure an in-memory test-only UserDetailsService with test users to test
        // Spring Security.
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
