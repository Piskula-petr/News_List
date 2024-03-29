package cz.news_list.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.news_list.article_builder.ArticleBuilder;
import cz.news_list.pojo.Article;

@Controller
public class ForeignPageController {
	
	@Autowired
	private ArticleBuilder articleBuilder;
	
	/**
	 * Zobrazení článků ze sekce zahraniční
	 * 
	 * @param model
	 * 
	 * @return - vrací název jsp stránky
	 */
	@RequestMapping(value = "/zahranicni", method = RequestMethod.GET)
	public String showForeignPage(Model model) {
		
		List<Article> articles = articleBuilder.getForeignArticles();

		model.addAttribute("articles", articles);
		
		return "indexPage";
	}
	
}
