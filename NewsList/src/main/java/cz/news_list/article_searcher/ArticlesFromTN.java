package cz.news_list.article_searcher;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import cz.news_list.pojo.Article;

public class ArticlesFromTN {
	
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
	 * 	Metoda vyselektuje nejnovější články ze zadaného URL serveru
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
				Element element = Jsoup.connect(serverURL).timeout(1000).get().select("div.article-with-preview").get(indexOfArticle);

				// Přeskakování promo článků
				while (element.hasClass("topic-new-item-promo")) {
					element = Jsoup.connect(serverURL).timeout(1000).get().select("div.article-with-preview").get(indexOfArticle++);
				}
				
				// Link článku
				String articleLink = element.select("h2 a").attr("href");
				
				// Obrázek článku
				String articleImage = element.select("img").attr("data-original-srcset");
				
				// Název článku
				String articleName = element.select("h2 a").text();
				
				// Datum článku
				String articleCreationDate = element.select("div.dateTime .date").text();
				
				// Úprava formátu datumu před parsováním
				if (articleCreationDate.isEmpty()) {
					
					String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy "));
					articleCreationDate = today + element.select("div.dateTime .time").text();
					
				} else if (articleCreationDate.equals("včera")) {
					
					String yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy "));
					articleCreationDate = yesterday + element.select("div.dateTime .time").text();
					
				} else articleCreationDate = articleCreationDate + LocalDate.now().getYear() + " " + element.select("div.dateTime .time").text();
				
				// Přidání článků do Listu pokud má všechny parametry
				if (!articleLink.isEmpty() && !articleImage.isEmpty() && !articleName.isEmpty() && !articleCreationDate.isEmpty()) {
					
					Article article = new Article();
					article.setLink(articleLink);
					article.setImage(articleImage);
					article.setName(articleName);
					article.setSource("tn.cz");
					article.setCreationDate(LocalDateTime.parse(articleCreationDate, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
					article.setTopic(topic);
					
					// Vyhledání duplicitních článků
					boolean duplicateArticle = false;
					for (Article articleFromList : articles) {
						
						if (article.getName().equals(articleFromList.getName())) {
							
							duplicateArticle = true;
						}
					}
					
					// Přídání článku do Listu, pokud nebyl nalezený duplicitní v jiné sekci
					if (!duplicateArticle) {
						
						articles.add(article);
						articlesAdded++;
					}
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
