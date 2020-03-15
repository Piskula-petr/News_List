package cz.news_list.article_builder;

import java.util.ArrayList;
import java.util.List;

import cz.news_list.article_searcher.ArticleFromTN;
import cz.news_list.article_searcher.ArticlesFromIDNES;
import cz.news_list.article_searcher.ArticlesFromNOVINKY;
import cz.news_list.beans.Article;

public class ArticleBuilder {

	private ArticleFromTN articleFromTN;
	private ArticlesFromIDNES articlesFromIDNES;
	private ArticlesFromNOVINKY articlesFromNOVINKY;
	private List<Article> articles;
	
// Konstruktor //////////////////////////////////////////////////////////////////////////////////////
	
	public ArticleBuilder() {
		
		articleFromTN = new ArticleFromTN();
		articlesFromIDNES = new ArticlesFromIDNES();
		articlesFromNOVINKY = new ArticlesFromNOVINKY();
		articles = new ArrayList<>();
	}
	
	/**
	 * 	Metoda spouštící 3 vlákna podle zadaných runnablů
	 * 
	 * 	@param runnableTN - runnable TN.CZ
	 * 	@param runnableIDNES - runnable IDNES.CZ
	 * 	@param runnableNOVINKY - runnable NOVINKY.CZ
	 */
	private void startThreads(Runnable runnableTN, Runnable runnableIDNES, Runnable runnableNOVINKY) {
		
		try {
			
			// Vytvoření vláken
			Thread threadTN = new Thread(runnableTN);
			Thread threadIDNES = new Thread(runnableIDNES);
			Thread threadNOVINKY = new Thread(runnableNOVINKY);
			
			threadTN.start();
			threadIDNES.start();
			threadNOVINKY.start();

			threadTN.join();
			threadIDNES.join();
			threadNOVINKY.join();
			
			// Dodatečné spuštění vláken, pokud není dosašeno minimální množství článků
			while (articles.size() <= 9) {
				
				if (articleFromTN.getArticlesAdded() != articleFromTN.getNumberOfRequiredArticles()) {
					
					threadTN = new Thread(runnableTN);
					threadTN.start();
				}
				
				if (articlesFromIDNES.getArticlesAdded() != articlesFromIDNES.getNumberOfRequiredArticles()) {
					
					threadIDNES = new Thread(runnableIDNES);
					threadIDNES.start();
				}
				
				if (articlesFromNOVINKY.getArticlesAdded() != articlesFromNOVINKY.getNumberOfRequiredArticles()) {
					
					threadNOVINKY = new Thread(runnableNOVINKY);
					threadNOVINKY.start();
				}
				
				threadTN.join();
				threadIDNES.join();
				threadNOVINKY.join();
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
// Nejnovější články ////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 	@return - vrací List nejnovějších článků z každé sekce
	 */
	public List<Article> getNewestArticles() {
		
		// Runnable TN.CZ		
		Runnable runnableTN = new Runnable() {
			
			@Override
			public void run() {
				
				articleFromTN.getArticles(articles, articleFromTN.getForeignURL(), "Zahraniční", 1);
				articleFromTN.zeroingArticleData();
				
				articleFromTN.getArticles(articles, articleFromTN.getHomeURL(), "Domácí", 1);
				articleFromTN.zeroingArticleData();
				
				articleFromTN.getArticles(articles, articleFromTN.getEconomyURL(), "Ekonomika", 1);
				articleFromTN.zeroingArticleData();
				
				articleFromTN.getArticles(articles, articleFromTN.getKrimiURL(), "Krimi", 1);
				articleFromTN.zeroingArticleData();
				
				articleFromTN.getArticles(articles, articleFromTN.getWeatherURL(), "Počasí", 1);
				articleFromTN.zeroingArticleData();
			}
		};
		
		// Runnable IDNES.CZ
		Runnable runnableIDNES = new Runnable() {
			
			@Override
			public void run() {
				
				articlesFromIDNES.getArticles(articles, articlesFromIDNES.getForeignURL(), "Zahraniční", 1);
				articlesFromIDNES.zeroingArticleData();
				
				articlesFromIDNES.getArticles(articles, articlesFromIDNES.getHomeURL(), "Domácí", 1);
				articlesFromIDNES.zeroingArticleData();
				
				articlesFromIDNES.getArticles(articles, articlesFromIDNES.getEconomyURL(), "Ekonomika", 1);
				articlesFromIDNES.zeroingArticleData();
				
				articlesFromIDNES.getArticles(articles, articlesFromIDNES.getKrimiURL(), "Krimi", 1);
				articlesFromIDNES.zeroingArticleData();
				
				articlesFromIDNES.getArticles(articles, articlesFromIDNES.getWeatherURL(), "Počasí", 1);
				articlesFromIDNES.zeroingArticleData();
			}
		};
		
		// Runnable NOVINKY.CZ
		Runnable runnableNOVINKY = new Runnable() {
			
			@Override
			public void run() {
				
				articlesFromNOVINKY.getArticles(articles, articlesFromNOVINKY.getForeignURL(), "Zahraniční", 1);
				articlesFromNOVINKY.zeroingArticleData();
				
				articlesFromNOVINKY.getArticles(articles, articlesFromNOVINKY.getHomeURL(), "Domácí", 1);
				articlesFromNOVINKY.zeroingArticleData();
				
				articlesFromNOVINKY.getArticles(articles, articlesFromNOVINKY.getEconomyURL(), "Ekonomika", 1);
				articlesFromNOVINKY.zeroingArticleData();
				
				articlesFromNOVINKY.getArticles(articles, articlesFromNOVINKY.getKrimiURL(), "Krimi", 1);
				articlesFromNOVINKY.zeroingArticleData();
				
				articlesFromNOVINKY.getArticles(articles, articlesFromNOVINKY.getWeatherURL(), "Počasí", 1);
				articlesFromNOVINKY.zeroingArticleData();
			}
		};
		
		// Spuštění vláken 
		startThreads(runnableTN, runnableIDNES, runnableNOVINKY);
		
		return articles;
	}
	
// Domácí články ////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 	@return - vrací List článků ze sekce domácí
	 */
	public List<Article> getHomeArticles() {
		
		// Runnable TN.CZ
		Runnable runnableTN = new Runnable() {
			
			@Override
			public void run() {
				
				articleFromTN.getArticles(articles, articleFromTN.getHomeURL(), "Domácí", 4);
			}
		};
		
		// Runnable IDNES.CZ
		Runnable runnableIDNES = new Runnable() {
			
			@Override
			public void run() {
				
				articlesFromIDNES.getArticles(articles, articlesFromIDNES.getHomeURL(), "Domácí", 4);
			}
		};
		
		// Runnable NOVINKY.CZ
		Runnable runnableNOVINKY = new Runnable() {
			
			@Override
			public void run() {
				
				articlesFromNOVINKY.getArticles(articles, articlesFromNOVINKY.getHomeURL(), "Domácí", 4);
			}
		};
		
		// Spuštění vláken
		startThreads(runnableTN, runnableIDNES, runnableNOVINKY);
		
		return articles;
	}
	
// Zahraniční články ////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 	@return - vrací List článků ze sekce zahraniční
	 */
	public List<Article> getForeignArticles() {
		
		// Runnable TN.CZ
		Runnable runnableTN = new Runnable() {
			
			@Override
			public void run() {
				
				articleFromTN.getArticles(articles, articleFromTN.getForeignURL(), "Zahraniční", 4);
			}
		};
		
		// Runnable IDNES.CZ
		Runnable runnableIDNES = new Runnable() {
			
			@Override
			public void run() {
				
				articlesFromIDNES.getArticles(articles, articlesFromIDNES.getForeignURL(), "Zahraniční", 4);
			}
		};
		
		// Runnable NOVINKY.CZ
		Runnable runnableNOVINKY = new Runnable() {
			
			@Override
			public void run() {
				
				articlesFromNOVINKY.getArticles(articles, articlesFromNOVINKY.getForeignURL(), "Zahraniční", 4);
			}
		};
		
		// Spuštění vláken
		startThreads(runnableTN, runnableIDNES, runnableNOVINKY);
		
		return articles;
	}
	
// Ekonomické články ////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 	@return - vrací List článků ze sekce ekonomika
	 */
	public List<Article> getEconomyArticles() {
		
		// Runnable TN.CZ
		Runnable runnableTN = new Runnable() {
			
			@Override
			public void run() {
				
				articleFromTN.getArticles(articles, articleFromTN.getEconomyURL(), "Ekonomika", 4);
			}
		};
		
		// Runnable IDNES.CZ
		Runnable runnableIDNES =  new Runnable() {
			
			@Override
			public void run() {
				
				articlesFromIDNES.getArticles(articles, articlesFromIDNES.getEconomyURL(), "Ekonomika", 4);
			}
		};
		
		// Runnable NOVINKY.CZ
		Runnable runnableNOVINKY = new Runnable() {
			
			@Override
			public void run() {
				
				articlesFromNOVINKY.getArticles(articles, articlesFromNOVINKY.getEconomyURL(), "Ekonomika", 4);
			}
		};
		
		// Spuštění vláken
		startThreads(runnableTN, runnableIDNES, runnableNOVINKY);
		
		return articles;
	}
	
// Krimi články /////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 	@return - vrací List článků ze sekce krimi
	 */
	public List<Article> getKrimiArticles() {
		
		// Runnable TN.CZ
		Runnable runnableTN = new Runnable() {
			
			@Override
			public void run() {
				
				articleFromTN.getArticles(articles, articleFromTN.getKrimiURL(), "Krimi", 4);
			}
		};
		
		// Runnable IDNES.CZ
		Runnable runnableIDNES = new Runnable() {
			
			@Override
			public void run() {
				
				articlesFromIDNES.getArticles(articles, articlesFromIDNES.getKrimiURL(), "Krimi", 4);
			}
		};
		
		// Runnable NOVINKY.CZ
		Runnable runnableNOVINKY = new Runnable() {
			
			@Override
			public void run() {
				
				articlesFromNOVINKY.getArticles(articles, articlesFromNOVINKY.getKrimiURL(), "Krimi", 4);
			}
		};
		
		// Spuštění vláken
		startThreads(runnableTN, runnableIDNES, runnableNOVINKY);
		
		return articles;
	}
	
// Počasí články ////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 	@return - vrací List článků ze sekce počasí
	 */
	public List<Article> getWeatherArticles() {
		
		// Runnable TN.CZ
		Runnable runnableTN = new Runnable() {
			
			@Override
			public void run() {
				
				articleFromTN.getArticles(articles, articleFromTN.getWeatherURL(), "Počasí", 4);
			}
		};
		
		// Runnable IDNES.CZ
		Runnable runnableIDNES = new Runnable() {
			
			@Override
			public void run() {
				
				articlesFromIDNES.getArticles(articles, articlesFromIDNES.getWeatherURL(), "Počasí", 4);
			}
		};
		
		// Runnable NOVINKY.CZ
		Runnable runnableNOVINKY = new Runnable() {
			
			@Override
			public void run() {
				
				articlesFromNOVINKY.getArticles(articles, articlesFromNOVINKY.getWeatherURL(), "Počasí", 4);
			}
		};
		
		// Spuštění vláken
		startThreads(runnableTN, runnableIDNES, runnableNOVINKY);
		
		return articles;
	}

	/**
	 * 	@return - List článků
	 */
	public List<Article> getList() {
		return articles;
	}
	
}
