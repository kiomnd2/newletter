package kr.kiomn2.newsletter.domain.news;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "news_categories")
public class NewsCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String iconUrl;

    private Boolean active;

    private Long articleCount = 0L;

    private Long subscriberCount = 0L;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static NewsCategory create(NewCategoryCommand.CreateNewCategory requestCreate) {
        NewsCategory newsCategory = new NewsCategory();
        newsCategory.name = requestCreate.name();
        newsCategory.description = requestCreate.description();
        newsCategory.iconUrl = requestCreate.iconUrl();
        newsCategory.active = true;
        newsCategory.createdAt = LocalDateTime.now();
        newsCategory.updatedAt = LocalDateTime.now();
        return newsCategory;
    }

    public void increaseArticleCount() {
        this.articleCount++;
    }

    public void decreaseArticleCount() {
        if (this.articleCount > 0) {
            this.articleCount--;
        }
    }

    public void inActivate() {
    	this.active = false;
    }

    public void activate() {
        this.active = true;
    }
}

