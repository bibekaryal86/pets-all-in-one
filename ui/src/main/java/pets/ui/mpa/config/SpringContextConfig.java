package pets.ui.mpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import pets.database.config.MongoDbConfig;
import pets.service.config.CacheConfig;
import pets.service.config.ScheduleConfig;
import pets.ui.mpa.util.ContextProvider;

@Configuration
@Import({ MongoDbConfig.class, CacheConfig.class, ScheduleConfig.class })
@ComponentScan(basePackages = { 
		"pets.ui.mpa.connector", 
		"pets.ui.mpa.controller",
		"pets.ui.mpa.service", 
		"pets.ui.mpa.validator",
		"pets.database.service", 
		"pets.database.repository",
		"pets.service.service", 
		"pets.service.connector"})
@EnableWebMvc
public class SpringContextConfig implements WebMvcConfigurer {

	@Bean
	public ContextProvider appCtxProvider() {
		return new ContextProvider();
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("messages");
		return resourceBundleMessageSource;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}

	@Bean
	public ViewResolver getViewResolver() {
		TilesViewResolver tilesViewResolver = new TilesViewResolver();
		tilesViewResolver.setViewClass(TilesView.class);
		return tilesViewResolver;
	}

	@Bean
	public TilesConfigurer getTilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitionsFactoryClass(TilesConfig.class);
		tilesConfigurer.setCheckRefresh(true);
		TilesConfig.addDefinitions();
		return tilesConfigurer;
	}
}
