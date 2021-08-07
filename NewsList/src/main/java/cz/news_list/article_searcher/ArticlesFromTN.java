package cz.news_list.article_searcher;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
			while (articlesAdded != + numberOfArticles || index > 20) {
				
				// Blok článku
				Element element = document.select("div.article-with-preview").get(index);
				
				// Přeskakování promo článků
				while (element.hasClass("topic-new-item-promo")) {
					
					element = document.select("div.article-with-preview").get(index++);
				}
				
				// Název článku
				String articleName = element.select("h2 a").text();
				
				// Odkaz článku
				String articleLink = element.select("h2 a").attr("href");
				
				// Obrázek článku
				String articleImage = element.select("img").attr("data-original-srcset");
				
				// Datum článku
				String articleCreationDate = element.select("div.dateTime .date").text();
				
				// Úprava formátu datumu
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
					article.setName(articleName);
					article.setLink(articleLink);
					article.setImage(articleImage);
					article.setCreationDate(LocalDateTime.parse(articleCreationDate, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
					article.setSource("tn.cz");
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
	 * Vymazání Listu
	 */
	public void clearArticles() {
		
		articles.clear();
	}

}
