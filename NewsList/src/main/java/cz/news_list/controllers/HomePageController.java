package cz.news_list.controllers;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.news_list.article_builder.ArticleBuilder;
import cz.news_list.beans.Article;

@Controller
public class HomePageController {
	
	@RequestMapping(value = "/domaci", method = RequestMethod.GET)
	public String showHomePage(Model model) throws IOException {
		
		ArticleBuilder articleBuilder = new ArticleBuilder();
		articleBuilder.getHomeArticles();

		model.addAttribute("articles", Article.sortListByDate(articleBuilder.getList()));
		
		return "indexPage";
	}
	
}
