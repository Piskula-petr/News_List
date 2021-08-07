package cz.news_list.pojo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Article {
	
	private String name;
	private String link;
	private String image;
	private LocalDateTime creationDate;
	private String source;
	private String topic;
	
}
