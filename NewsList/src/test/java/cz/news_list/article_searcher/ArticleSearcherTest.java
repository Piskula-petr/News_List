package cz.news_list.article_searcher;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cz.news_list.article_builder.ArticleBuilder;

@RunWith(JUnit4.class)
public class ArticleSearcherTest {
	
	private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beansTest.xml");
	
// Nejnovější Test //////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void getLatestArticles() {
		
		System.out.println("__________________________");
		System.out.println("Nejnovější články START");
		
		ArticleBuilder articleBuilder = applicationContext.getBean("articleBuilder", ArticleBuilder.class);
		articleBuilder.getLatestArticles();
		
		// Podmínky splnění tetu
		assertNotNull(articleBuilder.getList());
		assertTrue(articleBuilder.getList().size() >= 9);
		
		System.out.println("Počet přidaných článků: " + articleBuilder.getList().size());
		System.out.println("Nejnovější články COMPLETE");
		System.out.println();
	}
	
// Domácí Test //////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void getHomeArticles() {
		
		System.out.println("__________________________");
		System.out.println("Domácí články START");
		
		ArticleBuilder articleBuilder = applicationContext.getBean("articleBuilder", ArticleBuilder.class);
		articleBuilder.getHomeArticles();
		
		// Podmínky splnění tetu
		assertNotNull(articleBuilder.getList());
		assertTrue(articleBuilder.getList().size() >= 9);
		
		System.out.println("Počet přidaných článků: " + articleBuilder.getList().size());
		System.out.println("Domácí články COMPLETE");
		System.out.println();
	}
	
// Zahraniční Test //////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void getForeignArticles() {
		
		System.out.println("Zahraniční články START");
		
		ArticleBuilder articleBuilder = applicationContext.getBean("articleBuilder", ArticleBuilder.class);
		articleBuilder.getForeignArticles();
		
		// Podmínky splnění tetu
		assertNotNull(articleBuilder.getList());
		assertTrue(articleBuilder.getList().size() >= 9);
		
		System.out.println("Počet přidaných článků: " + articleBuilder.getList().size());
		System.out.println("Zahraniční články COMPLETE");
		System.out.println();
	}
	
// Ekonomické Test //////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void getEconomyArticles() {
		
		System.out.println("__________________________");
		System.out.println("Ekonomické články START");
		
		ArticleBuilder articleBuilder = applicationContext.getBean("articleBuilder", ArticleBuilder.class);
		articleBuilder.getEconomyArticles();
		
		// Podmínky splnění tetu
		assertNotNull(articleBuilder.getList());
		assertTrue(articleBuilder.getList().size() >= 9);
		
		System.out.println("Počet přidaných článků: " + articleBuilder.getList().size());
		System.out.println("Ekonomické články COMPLETE");
		System.out.println();
	}
	
// Krimi Test ///////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void getKrimiArticles() {
		
		System.out.println("__________________________");
		System.out.println("Krimi články START");
		
		ArticleBuilder articleBuilder = applicationContext.getBean("articleBuilder", ArticleBuilder.class);
		articleBuilder.getKrimiArticles();
		
		// Podmínky splnění tetu
		assertNotNull(articleBuilder.getList());
		assertTrue(articleBuilder.getList().size() >= 9);
		
		System.out.println("Počet přidaných článků: " + articleBuilder.getList().size());
		System.out.println("Krimi články COMPLETE");
		System.out.println();
	}
	
// Počasí Test //////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void getWeatherArticles() {
		
		System.out.println("__________________________");
		System.out.println("Počasí články START");
		
		ArticleBuilder articleBuilder = applicationContext.getBean("articleBuilder", ArticleBuilder.class);
		articleBuilder.getWeatherArticles();
		
		// Podmínky splnění tetu
		assertNotNull(articleBuilder.getList());
		assertTrue(articleBuilder.getList().size() >= 9);
		
		System.out.println("Počet přidaných článků: " + articleBuilder.getList().size());
		System.out.println("Počasí články COMPLETE");
		System.out.println();
	}
	
}
