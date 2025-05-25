package com.sample.module.core.loyalty.impl;

import com.sample.module.core.loyalty.LoyaltyRuleService;
import com.sample.module.core.model.LoyaltyRuleModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.impl.DefaultFlexibleSearchService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultLoyaltyRuleService implements LoyaltyRuleService {

    @Autowired
    private FlexibleSearchService flexibleSearchService;

    @Override
    public LoyaltyRuleModel getRuleForCategory(List<CategoryModel> categories) {
        if (CollectionUtils.isEmpty(categories)) {
            return null;
        }

        final String query = "SELECT {r.pk} " +
                "FROM {LoyaltyRule AS r} " +
                "WHERE {r.category} IN (?categories)";

        final Map<String, Object> params = new HashMap<>();
        params.put("categories", categories);

        final FlexibleSearchQuery fsQuery = new FlexibleSearchQuery(query, params);
        fsQuery.setResultClassList(Collections.singletonList(LoyaltyRuleModel.class));
        fsQuery.setCount(1); // Only get first match

        List<LoyaltyRuleModel> results = flexibleSearchService.<LoyaltyRuleModel>search(fsQuery).getResult();
        return results.isEmpty() ? null : results.get(0);
    }

    public void setFlexibleSearchService(DefaultFlexibleSearchService flexibleSearchService) {
    }
}
