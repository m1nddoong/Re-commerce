package com.example.market.domain.shop.dto;

import com.example.market.domain.shop.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private Long categoryId;
    private String name;
    public static CategoryDto fromEntity(Category entity) {
        return CategoryDto.builder()
                .categoryId(entity.getId())
                .name(entity.getName())
                .build();
    }
}
