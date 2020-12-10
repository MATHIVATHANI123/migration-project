/**
 * 
 */
package com.grootan.migration.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @author Mathivathani
 *
 */
@Configuration
@ImportResource(locations = {"classpath:database.xml"})
public class DBConfiguration {
	@Bean("messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:MessageResources","classpath:QueryConstants");
		messageSource.setCacheSeconds(10);
		return messageSource;
	}
}
