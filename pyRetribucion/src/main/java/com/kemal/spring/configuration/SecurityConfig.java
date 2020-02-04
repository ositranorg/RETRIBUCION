package com.kemal.spring.configuration;

/*@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  */
public class SecurityConfig /*extends WebSecurityConfigurerAdapter */{

	/*@Autowired
	private _UserDetailsServiceImpl userDetailsServiceImpl;
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
		
		 String[] staticResources  =  {
			        "/css/**",
			        "/images/**",
			        "/fonts/**",
			        "/scripts/**",
			    };
		 
		 
		http
		.authorizeRequests()		
		 .antMatchers(staticResources).permitAll()
		.antMatchers("/css/**", "/js/**",  "/resources/static/**", "/webjars/**").permitAll()	
		.antMatchers("/recuperar-clave").permitAll()
		
		.antMatchers("/user/**").hasRole("USER")
		.antMatchers("/login*").permitAll()		
		.anyRequest().authenticated()
		.and()
		.formLogin()		
		.loginPage("/login").permitAll()
		.loginProcessingUrl("/login_in")
		.defaultSuccessUrl("/index", true)
		.failureUrl("/login?error=true")
		.permitAll()
		.and().logout().permitAll()
		.logoutSuccessUrl("/login?logout=true").deleteCookies("JSESSIONID").permitAll()
		.and().sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true).sessionRegistry(sessionRegistry());
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		SessionRegistry sessionRegistry = new SessionRegistryImpl();
		return sessionRegistry;
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.POST,"/api/contribuyente/**");
        web.ignoring().antMatchers(HttpMethod.POST,"/api/usuario/enviar-clave");
    }
	// Register HttpSessionEventPublisher
	@Bean
	public static ServletListenerRegistrationBean<EventListener> httpSessionEventPublisher() {
		return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authProvider());
	}*/
	 
}
