package com.omniesoft.commerce.persistence.projection.category;

import java.util.List;
import java.util.UUID;

public interface CategorySummary {
    UUID getId();

    String getLogoId();

    String getEngName();

    String getRusName();

    String getUkrName();

    List<SubCategorySummary> getSubCategories();
}
