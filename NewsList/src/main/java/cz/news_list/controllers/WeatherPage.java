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
public class WeatherPage {

	@Autowired
	private ArticleBuilder articleBuilder;
	
	
	/**
	 * Zobrazení článků ze sekce počasí
	 * 
	 * @param model
	 * 
	 * @return - vrací název jsp stránky
	 */
	@RequestMapping(value = "/pocasi", method = RequestMethod.GET)
	public String showWeatherPage(Model model) {
		
		List<Article> articles = articleBuilder.getWeatherArticles();

		model.addAttribute("articles", articles);
		
		return "indexPage";
	}
	
}
