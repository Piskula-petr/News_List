package cz.news_list.article_searcher;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import cz.news_list.Topic;
import cz.news_list.pojo.Article;
import lombok.Getter;

@Component
@Getter
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
	
	private List<Article> articles = new ArrayList<>();

	
	/**
	 * Přidání článků do Listu
	 * 
	 * @param serverURL - Link na server
	 * @param topic - Enum témat
	 * @param numberOfArticles - počet požadovaných článků
	 */
	public void addArticles(String serverURL, Topic topic, int numberOfArticles) {
		
		int articlesAdded = 0;
		
		try {
			
			int index = 0;
			
			URL url = new URL(serverURL);
			Document document = Jsoup.parse(url, 3000);
			
			// Procházení článků, dokud nebude splněno požadované množství, nebo omezení indexu
			while (articlesAdded != numberOfArticles || index > 20) {
				
				// Blok článku
				Element element = document.select("li[data-dot=clanek_" + (index) + "]").first();
				
				// Přeskakování článků bez Linku
				while (element.select("a").attr("href").isEmpty()) {
					
					element = document.select("li[data-dot=clanek_" + (index++) + "]").first();
				}
				
				// Název článku
				String articleName = element.select("h3").text();
				
				// Link článku
				String articleLink = element.select("a").attr("href");
				
				// Obrázek článku
				String articleImage = element.select("img").attr("src");
				
				// Datum článku
				LocalDateTime articleCreationDate = getArticleCreationDate(articleLink);
				
				// Přidání článků do Listu pokud má všechny parametry
				if (!articleLink.isEmpty() && !articleImage.isEmpty() && !articleName.isEmpty() && !articleCreationDate.toString().isEmpty()) {
					
					Article article = new Article();
					article.setName(articleName);
					article.setLink(articleLink);
					article.setImage(articleImage);
					article.setCreationDate(articleCreationDate);
					article.setSource("novinky.cz");
					article.setTopic(topic.toString());
					
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
				
				index++;
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
			// Rekurze, při chybě
			addArticles(serverURL, topic, numberOfArticles);
		}
	}
	
	
	/**
	 * Získání datumu článku
	 * 
	 * @param articleLink - Link na detail článku
	 * 
	 * @return - vrací datum publikace článku
	 */
	private LocalDateTime getArticleCreationDate(String articleLink) {
		
		LocalDateTime articleCreationDate = null;
		
		try {
			
			URL url = new URL(articleLink);
			
			articleCreationDate = LocalDateTime.parse(Jsoup.parse(url, 3000).select("meta[name=szn:hp-expire]").attr("content"), 
					DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
			// Rekurze, při chybě
			articleCreationDate = getArticleCreationDate(articleLink);
		}
		
		// Korekce datumu (některé články mají zítřejší datum)
		LocalDateTime today = LocalDateTime.now();
		
		while (articleCreationDate.isAfter(today)) {
			
			articleCreationDate = articleCreationDate.minusDays(1);
		}
		
		return articleCreationDate;
	}
	
	
	/**
	 * Vymazání Listu
	 */
	public void clearArticles() {
		
		articles.clear();
	}

}
