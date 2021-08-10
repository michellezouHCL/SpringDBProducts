package com.michelle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.michelle.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
		.antMatchers("/registration","/process_registration").permitAll()
		.antMatchers("/user/**").hasRole("USER")
		.antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated()
				.and()
				.formLogin().loginPage("/login").defaultSuccessUrl("/", true).permitAll()
				.and()
				.logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll()
				.and()
				.exceptionHandling().accessDeniedPage("/403").and().csrf().disable();

	}

	/*
	 * @Configuration
	 * 
	 * @Order(2) public static class WebSecurityConfig extends
	 * WebSecurityConfigurerAdapter {
	 * 
	 * @Override protected void configure(HttpSecurity http) throws Exception { http
	 * .antMatcher("/admin/**").authorizeRequests().antMatchers("/admin/**").
	 * authenticated().anyRequest()
	 * .hasRole("ADMIN").and().formLogin().loginPage("/login").successForwardUrl(
	 * "/admin/home").permitAll()
	 * .permitAll().and().logout().logoutUrl("/logout").logoutSuccessUrl(
	 * "/login?logout").permitAll().and()
	 * .exceptionHandling().accessDeniedPage("/403").and().csrf().disable();
	 * System.out.println("order 2"); }
	 * 
	 * }
	 * 
	 * @Configuration
	 * 
	 * @Order(1) public static class WebSecurityConfig2 extends
	 * WebSecurityConfigurerAdapter {
	 * 
	 * protected void configure(HttpSecurity http) throws Exception {
	 * 
	 * http.antMatcher("/user/**").authorizeRequests().antMatchers("/user/**").
	 * authenticated().anyRequest()
	 * .hasRole("USER").and().formLogin().loginPage("/login").successForwardUrl(
	 * "/user/home").permitAll().and()
	 * .logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll().
	 * and() .exceptionHandling().accessDeniedPage("/403").and().csrf().disable();
	 * System.out.println("order 1");
	 * 
	 * } }
	 */

}