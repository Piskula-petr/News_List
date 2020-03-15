package cz.news_list.article_searcher;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import cz.news_list.beans.Article;

public class ArticleFromTN {
	
	// Domácí
	private final String HOME_URL = "https://tn.nova.cz/tema/7564-domaci/";
	
	// Zahraniční
	private final String FOREIGN_URL = "https://tn.nova.cz/tema/4196-zahranici/";
	
	// Ekonomika
	private final String ECONOMY_URL = "https://tn.nova.cz/tema/2314-ekonomika/";
	
	// Krimi
	private final String KRIMI_URL = "https://tn.nova.cz/tema/7584-krimi/";
	
	// Počasí
	private final String WEATHER_URL = "https://tn.nova.cz/pocasi/";
	
	// Index článku
	private int indexOfArticle;
	
	// Počet přidaných článků
	private int articlesAdded;
	
	// Počet požadovaných článků
	private int numberOfRequiredArticles;
	
	/**
	 * 	Metoda vyselektuje nejnovější články ze zadané sekce
	 * 
	 * 	@param articles - vstupní List článků (prázdný / naplněný)
	 * 	@param serverURL - odkaz na sekci článků
	 * 	@param topic - sekce článků
	 * 	@param numberOfRequiredArticles - počet požadovaných článků
	 * 
	 * 	@return - vrací List článků 
	 */
	public List<Article> getArticles(List<Article> articles, String serverURL, String topic, int numberOfRequiredArticles) {
		
		this.numberOfRequiredArticles = numberOfRequiredArticles;
		
		try {
			
			// Procházení článku, dokud nebude splněno požadované množství přidaných článků
			while (articlesAdded != numberOfRequiredArticles) {
				
				// Blok článku
				Element element = Jsoup.connect(serverURL).timeout(1000).get().select("div.article_medium_perex").get(indexOfArticle);

				// Přeskakování promo článků
				while (element.parent().hasClass("article_promo")) {
					element = Jsoup.connect(serverURL).timeout(1000).get().select("div.article_medium_perex").get(indexOfArticle++);
				}
				
				// Link článku
				String articleLink = element.select("h3 a").attr("href");
				
				// Obrázek článku
				String articleImage = element.select("img").attr("data-original-srcset");
				
				// Název článku
				String articleName = element.select("h3").text();
				
				// Datum článku
				String articleCreationDate = element.select("div.imginfo").text();
				
				// Úprava formátu datumu před parsováním
				if (articleCreationDate.contains("dnes")) {
					String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'"));
					articleCreationDate = articleCreationDate.replaceAll("dnes ", today);
					articleCreationDate = articleCreationDate + ":00+01:00";

				} else if (articleCreationDate.contains("včera")) {
					String yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'"));
					articleCreationDate = articleCreationDate.replace("včera ", yesterday);
					articleCreationDate = articleCreationDate + ":00+01:00";
					
				} else articleCreationDate = Jsoup.connect(articleLink).timeout(1000).get().select("meta[property=article:published_time]").attr("content");
				
				// Přidání článků do Listu pokud má všechny parametry
				if (!articleLink.isEmpty() && !articleImage.isEmpty() && !articleName.isEmpty() && !articleCreationDate.isEmpty()) {
					
					Article article = new Article();
					article.setLink(articleLink);
					article.setImage(articleImage);
					article.setName(articleName);
					article.setSource("tn.cz");
					article.setCreationDate(LocalDateTime.parse(articleCreationDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssz")));
					article.setTopic(topic);
					
					articles.add(article);
					
					articlesAdded++;
				}
				
				indexOfArticle++;
			}
			
		} catch (IOException e) {
			System.out.println("ERROR TN " + topic);
		}
		
		return articles;
	}
	
	/**
	 * 	Vynulování indexu a počtu přidaných článků
	 */
	public void zeroingArticleData() {
		
		articlesAdded = 0;
		indexOfArticle = 0;
	}
	
// Geterry /////////////////////////////////////////////////////////////////////////////////////////

	public String getHomeURL() {
		return HOME_URL;
	}
	public String getForeignURL() {
		return FOREIGN_URL;
	}

	public String getEconomyURL() {
		return ECONOMY_URL;
	}

	public String getKrimiURL() {
		return KRIMI_URL;
	}

	public String getWeatherURL() {
		return WEATHER_URL;
	}

	public int getArticlesAdded() {
		return articlesAdded;
	}
	
	public int getIndexOfArticle() {
		return indexOfArticle;
	}

	public int getNumberOfRequiredArticles() {
		return numberOfRequiredArticles;
	}

}
