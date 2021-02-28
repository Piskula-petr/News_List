package cz.news_list.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cz.news_list.article_builder.ArticleBuilder;
import cz.news_list.article_searcher.ArticlesFromIDNES;
import cz.news_list.article_searcher.ArticlesFromNOVINKY;
import cz.news_list.article_searcher.ArticlesFromTN;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"cz.news_list.controllers", "cz.news_list.article_builder", "cz.news_list.article_searcher"})
public class ApplicationConfiguration implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("/**").addResourceLocations("/WEB-INF/");
	}
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		
		registry.jsp("/WEB-INF/jsp/", ".jsp");
	}
	
// Beans //////////////////////////////////////////////////////////////////////////////////
	
	@Bean
	public ArticleBuilder articleBuilder() {
		
		return new ArticleBuilder();
	}
	
	@Bean
	public ArticlesFromIDNES articlesFromIDNES() {
		
		return new ArticlesFromIDNES();
	}
	
	@Bean
	public ArticlesFromNOVINKY articlesFromNOVINKY() {
		
		return new ArticlesFromNOVINKY();
	}
	
	@Bean
	public ArticlesFromTN articlesFromTN() {
		
		return new ArticlesFromTN();
	}
	
}
