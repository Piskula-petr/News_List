package cz.news_list.pojo;

import java.time.LocalDateTime;

public class Article {
	
	private String name;
	private String link;
	private String image;
	private LocalDateTime creationDate;
	private String source;
	private String topic;
	
	
	/**
	 * Bezparametrový konstruktor
	 */
	public Article() {
		
	}
	
// Gettery + Settery /////////////////////////////////////////////////////////////////////////////////
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


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


	public LocalDateTime getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	public String getTopic() {
		return topic;
	}


	public void setTopic(String topic) {
		this.topic = topic;
	}
	
}
