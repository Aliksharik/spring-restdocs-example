package kz.pifagor.news.archive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity
                .ignoring()
                // All of Spring Security will ignore the requests
                .antMatchers(HttpMethod.GET,"/news/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Do .antMatchers("/**").authenticated() to customize protected URLs.
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        // Note that the CSRf token is disabled for all requests.
        http.csrf().disable();
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {

        // Set the inMemoryAuthentication object with the given credentials.
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        manager.createUser(
                User.withUsername("test_user")
                        .password(passwordEncoder().encode("pass"))
                        .roles("TEST_ROLE")
                        .build());

        manager.createUser(
                User.withUsername("admin")
                        .password(passwordEncoder().encode("pass"))
                        .roles("ADMIN")
                        .build());

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
