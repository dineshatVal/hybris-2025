package com.sample.module.core.customer.dao.impl;

import com.sample.module.core.customer.dao.CustomerDao;
import com.sample.module.core.model.CustomCustomerLocModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultCustomerDao implements CustomerDao {

    @Autowired
    private FlexibleSearchService flexibleSearchService;

    @Override
    public Pair<CustomerModel, CustomCustomerLocModel> findCustomerByEmail(String email) {
        final String customerQuery = "SELECT {pk} FROM {Customer} WHERE {originalUid} = ?email";
        final String customerLocQuery = "SELECT {ccl.pk}"+
                "FROM { Customer AS c JOIN CustomCustomerLoc AS ccl " + "ON {c.customCustomerLoc} = {ccl.pk} } WHERE {c.uid} = ?uid";

        FlexibleSearchQuery fsQuery = new FlexibleSearchQuery(customerQuery);
        fsQuery.addQueryParameter("email", email);

        CustomerModel customerModel = flexibleSearchService.searchUnique(fsQuery);
        if (customerModel == null) {
            throw new IllegalArgumentException("Customer not found for email: " + email);
        }

        FlexibleSearchQuery fsLocQuery = new FlexibleSearchQuery(customerLocQuery);
        fsLocQuery.addQueryParameter("uid", customerModel.getUid());

        CustomCustomerLocModel customCustomerLocModel = flexibleSearchService.searchUnique(fsLocQuery);
        if (customCustomerLocModel == null) {
            throw new IllegalArgumentException("CustomCustomerLoc not found for customer UID: " + customerModel.getUid());
        }
        return Pair.of(customerModel,customCustomerLocModel);
    }
}
