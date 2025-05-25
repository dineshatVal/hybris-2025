package com.sample.module.core.loyalty;

import com.sample.module.core.model.LoyaltyRuleModel;
import de.hybris.platform.category.model.CategoryModel;
import java.util.List;

public interface LoyaltyRuleService {
    LoyaltyRuleModel getRuleForCategory(List<CategoryModel> categories);
}
