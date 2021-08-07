package cz.news_list.article_builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.news_list.Topic;
import cz.news_list.article_searcher.ArticlesFromIDNES;
import cz.news_list.article_searcher.ArticlesFromNOVINKY;
import cz.news_list.article_searcher.ArticlesFromTN;
import cz.news_list.pojo.Article;

@Component
public class ArticleBuilder {

	@Autowired
	private ArticlesFromIDNES articlesFromIDNES;
	
	@Autowired
	private ArticlesFromNOVINKY articlesFromNOVINKY;
	
	@Autowired
	private ArticlesFromTN articlesFromTN;
	
	private List<Article> articles = new ArrayList<>();
	
	
	/**
	 * Spuštění vláken
	 * 
	 * @param runnableIDNES - Runnable pro získání článků z IDNES.CZ
	 * @param runnableNOVINKY - Runnable pro získání článků z NOVINKY.CZ
	 * @param runnableTN - Runnable pro získání článků z TN.CZ
	 */
	private void startThreads(Runnable runnableIDNES, Runnable runnableNOVINKY, Runnable runnableTN) {

		// Vymazání předchozích článků
		articlesFromIDNES.clearArticles();
		articlesFromNOVINKY.clearArticles();
		articlesFromTN.clearArticles();
		
		articles.clear();
		
		try {
			
			Thread threadIDNES = new Thread(runnableIDNES);
			Thread threadNOVINKY = new Thread(runnableNOVINKY);
			Thread threadTN = new Thread(runnableTN);
			
			threadIDNES.start();
			threadNOVINKY.start();
			threadTN.start();
			
			threadIDNES.join();
			threadNOVINKY.join();
			threadTN.join();
			
			// Seskupení článků do Listu
			articles.addAll(articlesFromIDNES.getArticles());
			articles.addAll(articlesFromNOVINKY.getArticles());
			articles.addAll(articlesFromTN.getArticles());
			
			// Setřídění článků od nejnovějších po nejstarší
			Collections.sort(articles, (article1, article2) -> article1.getCreationDate().compareTo(article2.getCreationDate()));
			Collections.reverse(articles);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Získání nejnovějších článků
	 * 
	 * @return - vrací List nejnovějších článků
	 */
	public List<Article> getLatestArticles() {
		
		// Runnable IDNES.CZ
		Runnable runnableIDNES = new Runnable() {
			
			@Override
			public void run() {

				articlesFromIDNES.addArticles(articlesFromIDNES.getHOME_URL(), Topic.HOME, 1);
				articlesFromIDNES.addArticles(articlesFromIDNES.getFOREIGN_URL(), Topic.FOREIGN, 1);
				articlesFromIDNES.addArticles(articlesFromIDNES.getECONOMY_URL(), Topic.ECONOMY, 1);
				articlesFromIDNES.addArticles(articlesFromIDNES.getKRIMI_URL(), Topic.KRIMI, 1);
				articlesFromIDNES.addArticles(articlesFromIDNES.getWEATHER_URL(), Topic.WEATHER, 1);
			}
		};
		
		// Runnable NOVINKY.CZ
		Runnable runnableNOVINKY = new Runnable() {
			
			@Override
			public void run() {

				articlesFromNOVINKY.addArticles(articlesFromNOVINKY.getHOME_URL(), Topic.HOME, 1);
				articlesFromNOVINKY.addArticles(articlesFromNOVINKY.getFOREIGN_URL(), Topic.FOREIGN, 1);
				articlesFromNOVINKY.addArticles(articlesFromNOVINKY.getECONOMY_URL(), Topic.ECONOMY, 1);
				articlesFromNOVINKY.addArticles(articlesFromNOVINKY.getKRIMI_URL(), Topic.KRIMI, 1);
				articlesFromNOVINKY.addArticles(articlesFromNOVINKY.getWEATHER_URL(), Topic.WEATHER, 1);
			}
		};
		
		// Runnable TN.CZ
		Runnable runnableTN = new Runnable() {
			
			@Override
			public void run() {

				articlesFromTN.addArticles(articlesFromTN.getHOME_URL(), Topic.HOME, 1);
				articlesFromTN.addArticles(articlesFromTN.getFOREIGN_URL(), Topic.FOREIGN, 1);
				articlesFromTN.addArticles(articlesFromTN.getECONOMY_URL(), Topic.ECONOMY, 1);
				articlesFromTN.addArticles(articlesFromTN.getKRIMI_URL(), Topic.KRIMI, 1);
				articlesFromTN.addArticles(articlesFromTN.getWEATHER_URL(), Topic.WEATHER, 1);
			}
		};
		
		// Spuštění vláken
		startThreads(runnableIDNES, runnableNOVINKY, runnableTN);
		
		return articles;
	}
	
	
	/**
	 * Získání článků ze sekce domácí
	 * 
	 * @return - vrací List článků ze sekce domácí
	 */
	public List<Article> getHomeArticles() {
		
		// Runnable INDES.CZ
		Runnable runnableIDNES = new Runnable() {
			
			@Override
			public void run() {

				articlesFromIDNES.addArticles(articlesFromIDNES.getHOME_URL(), Topic.HOME, 4);
			}
		};
		
		// Runnable NOVINKY.CZ
		Runnable runnableNOVINKY = new Runnable() {
			
			@Override
			public void run() {
				
				articlesFromNOVINKY.addArticles(articlesFromNOVINKY.getHOME_URL(), Topic.HOME, 4);
			}
		};
		
		// Runnable TN.CZ
		Runnable runnableTN = new Runnable() {
			
			@Override
			public void run() {
				
				articlesFromTN.addArticles(articlesFromTN.getHOME_URL(), Topic.HOME, 4);
			}
		};
		
		// Spuštění vláken
		startThreads(runnableIDNES, runnableNOVINKY, runnableTN);
		
		return articles;
	}
	
	
	/**
	 * Získání článků ze sekce zahraniční
	 * 
	 * @return - vrací List článků ze sekce zahraniční
	 */
	public List<Article> getForeignArticles() {
		
		// Runnable INDES.CZ
		Runnable runnableIDNES = new Runnable() {
			
			@Override
			public void run() {
				
				articlesFromIDNES.addArticles(articlesFromIDNES.getFOREIGN_URL(), Topic.FOREIGN, 4);
			}
		};
		
		// Runnable NOVINKY.CZ
		Runnable runnableNOVINKY = new Runnable() {
			
			@Override
			public void run() {

				articlesFromNOVINKY.addArticles(articlesFromNOVINKY.getFOREIGN_URL(), Topic.FOREIGN, 4);
			}
		};
		
		// Runnable TN.CZ
		Runnable runnableTN = new Runnable() {
			
			@Override
			public void run() {

				articlesFromTN.addArticles(articlesFromTN.getFOREIGN_URL(), Topic.FOREIGN, 4);
			}
		};
		
		// Spuštění vláken
		startThreads(runnableIDNES, runnableNOVINKY, runnableTN);
		
		return articles;
	}
	
	
	/**
	 * Získání článku ze sekce ekonomika
	 * 
	 * @return vrací List článků ze sekce ekonomika
	 */
	public List<Article> getEconomyArticles() {
		
		// Runnable INDES.CZ
		Runnable runnableIDNES = new Runnable() {
			
			@Override
			public void run() {
				
				articlesFromIDNES.addArticles(articlesFromIDNES.getECONOMY_URL(), Topic.ECONOMY, 4);
			}
		};
		
		// Runnable NOVINKY.CZ
		Runnable runnableNOVINKY = new Runnable() {
			
			@Override
			public void run() {

				articlesFromNOVINKY.addArticles(articlesFromNOVINKY.getECONOMY_URL(), Topic.ECONOMY, 4);
			}
		};
		
		// Runnable TN.CZ
		Runnable runnableTN = new Runnable() {
			
			@Override
			public void run() {

				articlesFromTN.addArticles(articlesFromTN.getECONOMY_URL(), Topic.ECONOMY, 4);
			}
		};
		
		// Spuštění vláken
		startThreads(runnableIDNES, runnableNOVINKY, runnableTN);
		
		return articles;
	}
	
	
	/**
	 * Získání článků ze sekce krimi
	 * 
	 * @return - vrací List článků ze sekce krimi
	 */
	public List<Article> getKrimiArticles() {
		
		// Runnable IDNES.CZ
		Runnable runnableIDNES = new Runnable() {
			
			@Override
			public void run() {
				
				articlesFromIDNES.addArticles(articlesFromIDNES.getKRIMI_URL(), Topic.KRIMI, 4);
			}
		};
		
		// Runnable NOVINKY.CZ 
		Runnable runnableNOVINKY = new Runnable() {
			
			@Override
			public void run() {

				articlesFromNOVINKY.addArticles(articlesFromNOVINKY.getKRIMI_URL(), Topic.KRIMI, 4);
			}
		};
		
		// Runnable TN.CZ
		Runnable runnableTN = new Runnable() {
			
			@Override
			public void run() {

				articlesFromTN.addArticles(articlesFromTN.getKRIMI_URL(), Topic.KRIMI, 4);
			}
		};
		
		// Spuštění vláken
		startThreads(runnableIDNES, runnableNOVINKY, runnableTN);
		
		return articles;
	}
	
	
	/**
	 * Získání článků ze sekce počasí
	 *  
	 * @return - vrací List článků ze sekce počasí
	 */
	public List<Article> getWeatherArticles() {
	
		// Runnable IDNES.CZ
		Runnable runnableIDNES = new Runnable() {
			
			@Override
			public void run() {
				
				articlesFromIDNES.addArticles(articlesFromIDNES.getWEATHER_URL(), Topic.WEATHER, 4);
			}
		};
		
		// Runnable NOVINKY.CZ
		Runnable runnableNOVINKY = new Runnable() {
			
			@Override
			public void run() {

				articlesFromNOVINKY.addArticles(articlesFromNOVINKY.getWEATHER_URL(), Topic.WEATHER, 4);
			}
		};
		
		// Runnable TN.CZ
		Runnable runnableTN = new Runnable() {
			
			@Override
			public void run() {

				articlesFromTN.addArticles(articlesFromTN.getWEATHER_URL(), Topic.WEATHER, 4);
			}
		};
		
		// Spuštění vláken
		startThreads(runnableIDNES, runnableNOVINKY, runnableTN);
		
		return articles;
	}
	
}
