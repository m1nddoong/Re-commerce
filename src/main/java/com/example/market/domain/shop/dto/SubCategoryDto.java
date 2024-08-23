package com.example.market.domain.shop.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCategoryDto {
    private String categoryId;
    private String categoryName;
    private List<String> subCategoryName;
}
