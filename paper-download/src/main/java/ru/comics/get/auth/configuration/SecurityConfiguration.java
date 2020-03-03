package ru.comics.get.auth.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true
)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AccessDeniedHandler accessDeniedHandler;

    public SecurityConfiguration(AccessDeniedHandler accessDeniedHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(new BearerTokenAuthFilter(), BasicAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new BearerTokenAuthEntryPoint())
                .accessDeniedHandler(accessDeniedHandler);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.csrf().disable()
                .cors()
                .and()
                .headers()
                .frameOptions().disable()
                .contentTypeOptions().disable()
                .httpStrictTransportSecurity().disable()
                .xssProtection().disable();

        http.authorizeRequests()
                .antMatchers("/users/sessions").permitAll()
                .antMatchers("/users").permitAll()
                .anyRequest().authenticated();
        http.logout().disable();
    }
}
