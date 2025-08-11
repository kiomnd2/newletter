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

    private Integer categoryOrder;

    private Boolean active;

    private Long articleCount = 0L;

    private Long subscriberCount = 0L;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

