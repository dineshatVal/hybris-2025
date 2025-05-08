package com.sample.module.core.customer.dao.impl;

import com.sample.module.core.customer.dao.CustomCustomerLocDao;
import com.sample.module.core.model.CustomCustomerLocModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;

@Repository
public class DefaultCustomCustomerLocDao implements CustomCustomerLocDao {



    @Autowired
    private FlexibleSearchService flexibleSearchService;

    @Override
    public int getMaxUid() {
        final String query = "SELECT MAX(CAST({uid} AS int)) FROM {" + CustomCustomerLocModel._TYPECODE + "}";

        FlexibleSearchQuery fsq = new FlexibleSearchQuery(query);
        fsq.setResultClassList(Collections.singletonList(Integer.class));

        SearchResult<Integer> result = flexibleSearchService.search(fsq);

        if (result.getResult() != null && !result.getResult().isEmpty()) {
            Integer max = result.getResult().get(0);
            return max != null ? max : 0;
        }
        return 0;
    }
}
