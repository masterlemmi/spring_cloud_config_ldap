package com.example.demo.main.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${ALLOWED_ORIGINS}")
    private String[] ALLOWED_ORIGINS;

    @Value("${ALLOWED_ROLE}")
    private String ALLOWED_ROLE;

    @Autowired
    LdapUserDetailsMapper ldapUserDetailsMapper;

    @Bean
    @ConfigurationProperties(prefix = "ldap")
    public LdapSettings ldapSettings() {
        return new LdapSettings();
    }

    @Bean
    public AuthenticationEntryPoint restAuthenticationEntryPoint(){
        return (HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)  -> {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Unauthorized");
            };
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .cors()
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(restAuthenticationEntryPoint())
                .and()
                    .authorizeRequests()
                    .antMatchers("/hello").authenticated()
                    .antMatchers("/encrypt", "/decrypt").hasRole(ALLOWED_ROLE)
                    .antMatchers("/login").permitAll()
                .and()
                    .csrf().disable()    
                    //.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                    .formLogin()
                    .successHandler(mySuccessHandler())
                    .failureHandler(myFailureHandler())
                .and()
                    .logout()
                    .deleteCookies("JSESSIONID")
                    .logoutUrl("/api/logout")
                    .logoutSuccessHandler(
                            (HttpServletRequest request, HttpServletResponse response, Authentication authentication) ->  System.out.println("Logout Success"));
    }

    private AuthenticationFailureHandler myFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

    private AuthenticationSuccessHandler mySuccessHandler() {
        return new MySavedRequestAwareAuthenticationSuccessHandler();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(ALLOWED_ORIGINS));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth
            .ldapAuthentication()
            .contextSource(contextSource())
            .userSearchBase(ldapSettings().getUserBase())
            .userSearchFilter(ldapSettings().getUserFilter())
            .groupSearchBase(ldapSettings().getGroupBase())
            .groupSearchFilter(ldapSettings().getGroupFilter())
            .userDetailsContextMapper(ldapUserDetailsMapper);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public BaseLdapPathContextSource contextSource() {
        LdapContextSource bean = new LdapContextSource();
        bean.setUrl(ldapSettings().getUrl());
        bean.setBase(ldapSettings().getBase());
        bean.setUserDn(ldapSettings().getManagerDn());
        bean.setPassword(ldapSettings().getManagerPw());
        bean.setPooled(true);
        bean.setReferral("follow");
        bean.afterPropertiesSet();
        return bean;
    }
}
