package com.kemal.spring.configuration;

import java.nio.charset.Charset;
import java.util.EventListener;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.kemal.spring.service.userDetails.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// Beans
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public DaoAuthenticationProvider authProvider() {
		final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsServiceImpl);
		authProvider.setPasswordEncoder(bCryptPasswordEncoder);
	
		return authProvider;
	}

//    @Bean
//    public RememberMeServices rememberMeServices() {
//        return new CustomRememberMeServices("theKey",
//                userDetailsServiceImpl, new InMemoryTokenRepositoryImpl());
//    }

	// Override methods
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/css/**", "/js/**", "/images/**", "/resources/static/**", "/webjars/**").permitAll()
		.antMatchers("/user/**").hasRole("USER").antMatchers("/login*").permitAll().anyRequest().authenticated().and().formLogin()

		.loginPage("/login").permitAll()
		.loginProcessingUrl("/login_in")
		.defaultSuccessUrl("/index", true)
		.failureUrl("/login?error=true")
		
		.permitAll()
		.and().logout()
		.logoutSuccessUrl("/login?logout=true").deleteCookies("JSESSIONID").permitAll()
		.and().sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true).sessionRegistry(sessionRegistry());
	}
	
	@Bean
	public SessionRegistry sessionRegistry() {
		SessionRegistry sessionRegistry = new SessionRegistryImpl();
		return sessionRegistry;
	}
	

	// Register HttpSessionEventPublisher
	@Bean
	public static ServletListenerRegistrationBean<EventListener> httpSessionEventPublisher() {
		return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authProvider());
	}
	
}
