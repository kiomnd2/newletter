package kr.kiomn2.newsletter.domain.news;

import jakarta.validation.constraints.NotNull;

public class NewCategoryCommand {

    public record CreateNewCategory(@NotNull String name, String description, String iconUrl) {
    }
}
