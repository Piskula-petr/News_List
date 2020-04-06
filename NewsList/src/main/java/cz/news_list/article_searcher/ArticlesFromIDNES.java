package cz.news_list.article_searcher;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import cz.news_list.beans.Article;

public class ArticlesFromIDNES {
	
	// Domácí
	private final String HOME_URL = "https://www.idnes.cz/zpravy/domaci";
	
	// Zahraniční
	private final String FOREIGN_URL = "https://www.idnes.cz/zpravy/zahranicni";
	
	// Ekonomika
	private final String ECONOMY_URL = "https://www.idnes.cz/ekonomika";
	
	// Krimi
	private final String KRIMI_URL = "https://www.idnes.cz/zpravy/cerna-kronika";
	
	// Počasí
	private final String WEATHER_URL = "https://pocasi.idnes.cz/";
	
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
				Element element = Jsoup.connect(serverURL).timeout(1000).get().select("div.art").get(indexOfArticle);
				
				// Přeskakování prémiových článků			     // Přeskakování článků bez Linku										// Přeskakování onclick funkcí
				while (element.children().hasClass("premlab") || element.select("a.art-link").not("[target]").attr("href").isEmpty() || element.select("a.art-link").not("[target]").hasAttr("onclick")) {
					element = Jsoup.connect(serverURL).timeout(1000).get().select("div.art").get(indexOfArticle++);
				}
				
				// Link článku
				String articleLink = element.select("a.art-link").not("[target]").attr("href");		// atribut target = reklamní článek
				
				// Obrázek článku
				String articleImage = element.select("a.art-link").not("[target]").select("img").attr("src");
				
				// Pokud obrázek není v src atributu
				if (articleImage.isEmpty()) {
					
					// style="background-image: url(//...URL OBRÁZKU...)
					articleImage = element.select("u").attr("style");
					
					// začátek URL obrázku
					int imageStartIndex = articleImage.indexOf("(") + 1;
					
					// Konečný řetězec obrázku
					articleImage = articleImage.substring(imageStartIndex, articleImage.length() - 1);
				}
				
				// Název článku
				String articleName = element.select("a.art-link").not("[target]").text();
				
				// Datum článku
				String documentArticle = Jsoup.connect(articleLink).timeout(1000).get().select("span.time-date").attr("content");
				LocalDateTime articleCreationDate = LocalDateTime.parse(documentArticle, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmz"));
				
				// Přidání článků do Listu pokud má všechny parametry
				if (!articleLink.isEmpty() && !articleImage.isEmpty() && !articleName.isEmpty() && !articleCreationDate.toString().isEmpty()) {
					
					Article article = new Article();
					article.setLink(articleLink);
					article.setImage(articleImage);
					article.setName(articleName);
					article.setSource("idnes.cz");
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
			System.out.println("ERROR IDNES " + topic);
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
