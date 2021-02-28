package cz.news_list.article_searcher;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cz.news_list.article_builder.ArticleBuilder;
import cz.news_list.configuration.ApplicationConfiguration;
import cz.news_list.pojo.Article;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class ArticleSearcherTest {
	
	private List<Article> articles = new ArrayList<>();
	
	@Autowired
	private ArticleBuilder articleBuilder;
	
// Nejnovější Test //////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void getLatestArticles() {
		
		System.out.println("__________________________");
		System.out.println("Nejnovější články START");
		
		articles = articleBuilder.getLatestArticles();
		
		// Podmínky splnění tetu
		assertNotNull(articles);
		assertTrue(articles.size() == 15);
		
		System.out.println("Počet přidaných článků: " + articles.size());
		System.out.println("Nejnovější články COMPLETE");
		System.out.println();
	}
	
// Domácí Test //////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void getHomeArticles() {
		
		System.out.println("__________________________");
		System.out.println("Domácí články START");
		
		articles = articleBuilder.getHomeArticles();
		
		// Podmínky splnění tetu
		assertNotNull(articles);
		assertTrue(articles.size() == 12);
		
		System.out.println("Počet přidaných článků: " + articles.size());
		System.out.println("Domácí články COMPLETE");
		System.out.println();
	}
	
// Zahraniční Test //////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void getForeignArticles() {
		
		System.out.println("Zahraniční články START");
		
		articles = articleBuilder.getForeignArticles();
		
		// Podmínky splnění tetu
		assertNotNull(articles);
		assertTrue(articles.size() == 12);
		
		System.out.println("Počet přidaných článků: " + articles.size());
		System.out.println("Zahraniční články COMPLETE");
		System.out.println();
	}
	
// Ekonomické Test //////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void getEconomyArticles() {
		
		System.out.println("__________________________");
		System.out.println("Ekonomické články START");
		
		articles = articleBuilder.getEconomyArticles();
		
		// Podmínky splnění tetu
		assertNotNull(articles);
		assertTrue(articles.size() == 12);
		
		System.out.println("Počet přidaných článků: " + articles.size());
		System.out.println("Ekonomické články COMPLETE");
		System.out.println();
	}
	
// Krimi Test ///////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void getKrimiArticles() {
		
		System.out.println("__________________________");
		System.out.println("Krimi články START");
		
		articles = articleBuilder.getKrimiArticles();
		
		// Podmínky splnění tetu
		assertNotNull(articles);
		assertTrue(articles.size() == 12);
		
		System.out.println("Počet přidaných článků: " + articles.size());
		System.out.println("Krimi články COMPLETE");
		System.out.println();
	}
	
// Počasí Test //////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void getWeatherArticles() {
		
		System.out.println("__________________________");
		System.out.println("Počasí články START");
		
		articles = articleBuilder.getWeatherArticles();
		
		// Podmínky splnění tetu
		assertNotNull(articles);
		assertTrue(articles.size() == 12);
		
		System.out.println("Počet přidaných článků: " + articles.size());
		System.out.println("Počasí články COMPLETE");
		System.out.println();
	}
	
}
