package cz.fencl.minicrm.application.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Configuration
	public static class DefaultConfiguration extends WebSecurityConfigurerAdapter {

		public DefaultConfiguration() {
			log.info("default configuration started");
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			log.info("default configuration configured");
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
					.authorizeRequests().antMatchers("/**").permitAll().and()
					.headers().addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*")).and()
					.csrf().disable();
		}
	}

}
