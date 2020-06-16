package cz.news_list.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.news_list.article_builder.ArticleBuilder;
import cz.news_list.pojo.Article;

@Controller
public class ForeignPage {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@RequestMapping(value = "/zahranicni", method = RequestMethod.GET)
	public String showForeignPage(Model model) throws IOException {
		
		ArticleBuilder articleBuilder = applicationContext.getBean("articleBuilder", ArticleBuilder.class);
		articleBuilder.getForeignArticles();

		model.addAttribute("articles", Article.sortListByDate(articleBuilder.getList()));
		
		return "indexPage";
	}
	
}