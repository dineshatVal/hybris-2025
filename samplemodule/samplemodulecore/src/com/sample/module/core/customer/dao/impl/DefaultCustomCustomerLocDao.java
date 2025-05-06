package com.sample.module.core.customer.dao.impl;

import com.sample.module.core.customer.dao.CustomCustomerLocDao;
import com.sample.module.core.model.CustomCustomerLocModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.springframework.stereotype.Repository;

import java.util.Collections;

@Repository
public class DefaultCustomCustomerLocDao extends DefaultGenericDao<CustomCustomerLocModel> implements CustomCustomerLocDao {

    public DefaultCustomCustomerLocDao() {
        super(CustomCustomerLocModel._TYPECODE);
    }

    @Override
    public int getMaxUid() {
        final String query = "SELECT MAX(CAST({uid} AS int)) FROM {" + CustomCustomerLocModel._TYPECODE + "}";

        final FlexibleSearchQuery fsq = new FlexibleSearchQuery(query);
        fsq.setResultClassList(Collections.singletonList(Integer.class));

        SearchResult<Integer> result = getFlexibleSearchService().search(fsq);
        Integer max = result.getResult().get(0);
        return max != null ? max : 0;
    }
}
