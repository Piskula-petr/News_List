package cz.news_list.article_searcher;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import cz.news_list.beans.Article;

public class ArticlesFromNOVINKY {

	// Domácí
	private final String HOME_URL = "https://www.novinky.cz/domaci";
	
	// Zahraniční
	private final String FOREIGN_URL = "https://www.novinky.cz/zahranicni";
	
	// Ekonomika
	private final String ECONOMY_URL = "https://www.novinky.cz/ekonomika";

	// Krimi
	private final String KRIMI_URL = "https://www.novinky.cz/krimi";
	
	// Počasí
	private final String WEATHER_URL = "https://www.novinky.cz/pocasi";
	
	// Index článku
	private int indexOfArticle = 1;
	
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
				Element element = Jsoup.connect(serverURL).timeout(1000).get().select("li[data-dot=clanek_" + (indexOfArticle) + "]").first();
				
				// Přeskakování článků bez Linku
				while (element.select("a").attr("href").isEmpty()) {
					element = Jsoup.connect(serverURL).timeout(1000).get().select("li[data-dot=clanek_" + (indexOfArticle++) + "]").first();
				}
			
				// Link článku
				String articleLink = element.select("a").attr("href");
				
				// Obrázek článku
				String articleImage = element.select("img").attr("src");
				
				// Název článku
				String articleName = element.select("h3").text();
				
				// Datum článku
				LocalDateTime articleCreationDate = LocalDateTime.parse(Jsoup.connect(articleLink).timeout(1000).get().select("meta[name=szn:hp-expire]").attr("content"), 
													DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
						
				// Korekce datumu (některé články mají zítřejší datum)
				LocalDateTime today = LocalDateTime.now();
				while (articleCreationDate.isAfter(today)) {
					articleCreationDate = articleCreationDate.minusDays(1);
				}
				
				// Přidání článků do Listu pokud má všechny parametry
				if (!articleLink.isEmpty() && !articleImage.isEmpty() && !articleName.isEmpty() && !articleCreationDate.toString().isEmpty()) {
					
					Article article = new Article();
					article.setLink(articleLink);
					article.setImage(articleImage);
					article.setName(articleName);
					article.setSource("novinky.cz");
					article.setCreationDate(articleCreationDate);
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
			System.out.println("ERROR NOVINKY " + topic);
		}
		
		return articles;
	}
	
	/**
	 * 	Vynulování indexu a počtu přidaných článků
	 */
	public void zeroingArticleData() {
		
		articlesAdded = 0;
		indexOfArticle = 1;
	}
	
// Gettery //////////////////////////////////////////////////////////////////////////////////////////
	
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

	public int getIndexOfArticle() {
		return indexOfArticle;
	}

	public int getArticlesAdded() {
		return articlesAdded;
	}

	public int getNumberOfRequiredArticles() {
		return numberOfRequiredArticles;
	}

}
