package br.imd.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	DataSource dataSource;
	
	/*@Autowired
    private AccessDeniedHandler accessDeniedHandler;*/
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("USER");
		//auth.jdbcAuthentication().dataSource(dataSource);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception
	{
		http.antMatcher("/**").authorizeRequests().anyRequest().hasRole("USER")
				.and().formLogin().loginPage("/login")
				/*.failureUrl("login/index.html")*/.loginProcessingUrl("/login")
				.permitAll().and().logout()
				.logoutSuccessUrl("/home.html");
	}

}
