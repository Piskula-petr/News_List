package cz.news_list.beans;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Article {
	
	// Parametry článku
	private String link;
	private String image;
	private String name;
	private String source;
	private LocalDateTime creationDate;
	private String topic;
	
	private List<Article> articles;
	
// Bezparametrový konstruktor ////////////////////////////////////////////////////////////////////////

	public Article() {
		
	}

	/**
	 * 	Satická metoda setřídění Listu od nejnovějších článků
	 * 
	 * 	@param articles - List článků
	 * 	@return - setřízený List článků
	 */
	public static List<Article> sortListByDate(List<Article> articles) {
		
		Collections.sort(articles, (article1, article2) -> article1.getCreationDate().compareTo(article2.getCreationDate()));
		Collections.reverse(articles);
		
		return articles;
	}
	
// Gettery + Settery /////////////////////////////////////////////////////////////////////////////////
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
}
