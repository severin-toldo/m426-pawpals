package com.ateam.paw_pals.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.ateam.paw_pals.config.filter.JwtAuthorizationFilter;
import com.ateam.paw_pals.config.filter.JwtLoginFilter;
import com.ateam.paw_pals.config.filter.RequestLogFilter;
import com.ateam.paw_pals.service.UserEntityService;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
    private UserEntityService userEntityService;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@Value("${security.jwt.secret}")
	private String jwtSecret;
	
	@Value("${security.jwt.token-validity-in-minutes}")
	private Integer jwtTokenValidityInMinutes;
	
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.cors().disable();
    	http.csrf().disable();
    	http.headers().frameOptions().disable();
    	http.headers().frameOptions().sameOrigin();
    	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Disable Spring Session Creation
    	
    	http
    		.authorizeRequests()
    		.antMatchers("/api/users/register").permitAll()
    		.antMatchers("/login").permitAll()
    		.antMatchers("/h2-console/**").permitAll()
    		.antMatchers("/v3/**").permitAll()
			.antMatchers("/swagger-ui/**").permitAll()
			.antMatchers("/swagger-ui.html").permitAll()
    		.anyRequest().authenticated();
    	
    	http
    		.addFilter(new JwtLoginFilter(authenticationManager(), jwtSecret, jwtTokenValidityInMinutes))
    		.addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtSecret, userEntityService))
    		.addFilterBefore(new RequestLogFilter(), BasicAuthenticationFilter.class);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
				return userEntityService.getUserDetailsByUserName(email);
			}
		}).passwordEncoder(passwordEncoder);
    }
}
