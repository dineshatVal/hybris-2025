package com.sample.module.core.customer.dao;

import com.sample.module.core.model.CustomCustomerLocModel;
import de.hybris.platform.core.model.user.CustomerModel;
import org.apache.commons.lang3.tuple.Pair;

public interface CustomerDao {
    Pair<CustomerModel, CustomCustomerLocModel> findCustomerByEmail(String email);
}
