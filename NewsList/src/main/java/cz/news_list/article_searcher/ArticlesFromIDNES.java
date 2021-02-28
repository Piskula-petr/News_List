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

@Component
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
				Element element = document.select("a.art-link").not("[target]").get(index);
				
				// Přeskakování prémiových článků
				while (element.parent().select("a").hasClass("premlab") 
						
						// Omezení jen na články v kategorii
						|| !element.parent().hasClass("art") 
						
						// Přeskakování onClick funkcí
						// atribut [target] = reklamní článek
						|| element.select("a.art-link").not("[target]").hasAttr("onclick")) {
					
					element = document.select("a.art-link").not("[target]").get(index++);
				}
				
				// Název článku
				String articleName = element.select("a.art-link").not("[target]").text();
				
				// Link článku
				String articleLink = element.select("a.art-link").not("[target]").attr("href");
				
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
				
				// Datum článku
				LocalDateTime articleCreationDate = getArticleCreationDate(articleLink);
				
				// Přidání článků do Listu pokud má všechny parametry
				if (!articleLink.isEmpty() && !articleImage.isEmpty() && !articleName.isEmpty() && !articleCreationDate.toString().isEmpty()) {
					
					Article article = new Article();
					article.setName(articleName);
					article.setLink(articleLink);
					article.setImage(articleImage);
					article.setCreationDate(articleCreationDate);
					article.setSource("idnes.cz");
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
			
			URL articleUrl = new URL(articleLink);
			
			String articleDetail = Jsoup.parse(articleUrl, 3000).select("span.time-date").attr("content");
			articleCreationDate = LocalDateTime.parse(articleDetail, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mmz"));
			
		} catch (IOException e) {

			e.printStackTrace();
			
			// Rekurze, při chybě
			articleCreationDate = getArticleCreationDate(articleLink);
		}
		
		return articleCreationDate;
	}
	
	
	/**
	 * Vymazání Listu
	 */
	public void clearArticles() {
		
		articles.clear();
	}
	
// Gettery ///////////////////////////////////////////////////////////////////////////////
	
	public String getHOME_URL() {
		return HOME_URL;
	}

	public String getFOREIGN_URL() {
		return FOREIGN_URL;
	}

	public String getECONOMY_URL() {
		return ECONOMY_URL;
	}

	public String getKRIMI_URL() {
		return KRIMI_URL;
	}

	public String getWEATHER_URL() {
		return WEATHER_URL;
	}

	public List<Article> getArticles() {
		return articles;
	}
	
}
