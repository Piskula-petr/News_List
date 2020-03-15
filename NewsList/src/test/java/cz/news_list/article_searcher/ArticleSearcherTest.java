package cz.news_list.article_searcher;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import cz.news_list.article_builder.ArticleBuilder;

class ArticleSearcherTest {
	
// Nejnovější Test //////////////////////////////////////////////////////////////////////////////////
	
	@Test
	void getLatestArticles() {
		
		System.out.println("__________________________");
		System.out.println("Nejnovější články START");
		
		ArticleBuilder articleBuilder = new ArticleBuilder();
		articleBuilder.getLatestArticles();
		
		// Podmínky splnění tetu
		assertNotNull(articleBuilder.getList());
		assertTrue(articleBuilder.getList().size() >= 9);
		
		System.out.println("Počet přidaných článků: " + articleBuilder.getList().size());
		System.out.println("Nejnovější články FINISH");
		System.out.println();
	}
	
// Domácí Test //////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	void getHomeArticles() {
		
		System.out.println("__________________________");
		System.out.println("Domácí články START");
		
		ArticleBuilder articleBuilder = new ArticleBuilder();
		articleBuilder.getHomeArticles();
		
		// Podmínky splnění tetu
		assertNotNull(articleBuilder.getList());
		assertTrue(articleBuilder.getList().size() >= 9);
		
		System.out.println("Počet přidaných článků: " + articleBuilder.getList().size());
		System.out.println("Domácí články FINISH");
		System.out.println();
	}
	
// Zahraniční Test //////////////////////////////////////////////////////////////////////////////////
	
	@Test
	void getForeignArticles() {
		
		System.out.println("Zahraniční články START");
		
		ArticleBuilder articleBuilder = new ArticleBuilder();
		articleBuilder.getForeignArticles();
		
		// Podmínky splnění tetu
		assertNotNull(articleBuilder.getList());
		assertTrue(articleBuilder.getList().size() >= 9);
		
		System.out.println("Počet přidaných článků: " + articleBuilder.getList().size());
		System.out.println("Zahraniční články FINISH");
		System.out.println();
	}
	
// Ekonomické Test //////////////////////////////////////////////////////////////////////////////////
	
	@Test
	void getEconomyArticles() {
		
		System.out.println("__________________________");
		System.out.println("Ekonomické články START");
		
		ArticleBuilder articleBuilder = new ArticleBuilder();
		articleBuilder.getEconomyArticles();
		
		// Podmínky splnění tetu
		assertNotNull(articleBuilder.getList());
		assertTrue(articleBuilder.getList().size() >= 9);
		
		System.out.println("Počet přidaných článků: " + articleBuilder.getList().size());
		System.out.println("Ekonomické články FINISH");
		System.out.println();
	}
	
// Krimi Test ///////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	void getKrimiArticles() {
		
		System.out.println("__________________________");
		System.out.println("Krimi články START");
		
		ArticleBuilder articleBuilder = new ArticleBuilder();
		articleBuilder.getKrimiArticles();
		
		// Podmínky splnění tetu
		assertNotNull(articleBuilder.getList());
		assertTrue(articleBuilder.getList().size() >= 9);
		
		System.out.println("Počet přidaných článků: " + articleBuilder.getList().size());
		System.out.println("Krimi články FINISH");
		System.out.println();
	}
	
// Počasí Test //////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	void getWeatherArticles() {
		
		System.out.println("__________________________");
		System.out.println("Počasí články START");
		
		ArticleBuilder articleBuilder = new ArticleBuilder();
		articleBuilder.getWeatherArticles();
		
		// Podmínky splnění tetu
		assertNotNull(articleBuilder.getList());
		assertTrue(articleBuilder.getList().size() >= 9);
		
		System.out.println("Počet přidaných článků: " + articleBuilder.getList().size());
		System.out.println("Počasí články FINISH");
		System.out.println();
	}
	
}
