package com.dialoguemd.rest.authentication;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


//@Configuration
//@EnableWebSecurity
//public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
public class CustomWebSecurityConfigurerAdapter {
	
	//TODO - Can implement basic authentication
	
//	@Value("${spring.security.user.name}")
//	private String user;
//	
//	@Value("${spring.security.user.password}")
//	private String password;
	
	@Autowired
	SimpleAuthenticationEntryPoint simpleAuthenticationEntryPoint;
 
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();       
//        auth.inMemoryAuthentication()
//        	.withUser(user)
//        	.password(encoder.encode(password))
//        	.roles("USER")
//        	.and()
//        	.withUser("admin")
//        	.password(encoder.encode("admin"))
//        	.roles("USER", "ADMIN");
//    }
 
//   // @Override
//	//working for GET only
//    protected void configure2(HttpSecurity http) throws Exception {        
//        http.requiresChannel()
//        	.anyRequest()
//        	.requiresSecure()
//        	//.antMatchers(HttpMethod.POST, "/**")..hasRole("USER")
//        	.and().csrf().disable()
//        	;
//    }
    
    

    
//        
//    //@Override
//    protected void configure(HttpSecurity http) throws Exception{
//        http.csrf().disable();
//        
//        http.authorizeRequests()
//        .antMatchers("/*")
//        .permitAll();
//        
//        http.authorizeRequests()
//        .anyRequest()
//        .authenticated();
//        
//        http.requiresChannel()
//        .anyRequest().requiresSecure();
//    }
    
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
    
    
// 	@Override
// 	public void configure(WebSecurity web) throws Exception {
// 		web.ignoring()
// 		// Spring Security should completely ignore URLs starting with /resources/
// 				.antMatchers("/*");
// 	}
//
// 	@Override
// 	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
// 		auth
// 		// enable in memory based authentication with a user named "user" and "admin"
// 		.inMemoryAuthentication().withUser("user").password("password").roles("user")
// 				.and().withUser("admin").password("password").roles("user", "admin");
// 	}
   
}
