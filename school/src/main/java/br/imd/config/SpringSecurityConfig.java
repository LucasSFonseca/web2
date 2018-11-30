package br.imd.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	private ImplementsUserDetailsService userDetailsService;
	
	/*@Autowired
    private AccessDeniedHandler accessDeniedHandler;*/
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		//auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("USER");
		//auth.jdbcAuthentication().dataSource(dataSource);
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception
	{
				http.authorizeRequests()
				//.anyRequest().hasAnyRole("USER", "ADMIN")
				.antMatchers("/users/new").permitAll()
				.antMatchers(HttpMethod.POST, "/users").permitAll()
//				.antMatchers("/").hasAnyRole("ADMIN", "USER")
//				.antMatchers("/users").hasAnyRole("ADMIN", "USER")
//				.antMatchers("/users/*").hasAnyRole("ADMIN", "USER")
//				.antMatchers("/books").hasAnyRole("ADMIN", "USER")
//				.antMatchers("/collections").hasAnyRole("ADMIN", "USER")
//				.antMatchers("/wishlists").hasAnyRole("ADMIN", "USER")
//				.antMatchers("/swaps").hasAnyRole("ADMIN", "USER")
				.anyRequest().authenticated()
				.and().formLogin().loginPage("/login").defaultSuccessUrl("/", true)
				.failureUrl("/login")
				.loginProcessingUrl("/login")
				.permitAll().and()
				.logout()
	            .logoutUrl("/logout")
	            .logoutSuccessUrl("/login");
		

		http.csrf().disable();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers( "/img/**",
	        "/lib/**",
	        "/css/**");
	}
}
