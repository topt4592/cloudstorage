package com.udacity.jwdnd.course1.cloudstorage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationService authenticationProviderServices;

    private static final String[] AUTH_WHITELIST = { "/css/**", "/js/**", "/signup" };

    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests(
                requests -> requests.antMatchers(AUTH_WHITELIST).permitAll().anyRequest().authenticated());

        // Form login
        http.formLogin(login -> login.loginPage("/login").permitAll().defaultSuccessUrl("/home", true));

        // Logout
        http.logout(logout -> logout.logoutUrl("/logout").invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login?logout").permitAll());

        // Disabled CSRF protection
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/h2-console/**");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProviderServices);
    }
}
