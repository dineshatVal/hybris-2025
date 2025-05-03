package com.sample.module.core.customer.impl;

import com.sample.module.core.customer.CustomerModelService;
import com.sample.module.core.customer.dao.CustomerDao;
import com.sample.module.core.model.CustomCustomerLocModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component("customerModelService")
public class DefaultCustomerModelService implements CustomerModelService {

    @Autowired
    private FlexibleSearchService flexibleSearchService;

    @Resource(name = "customCustomerDao")
    private CustomerDao customCustomerDao;

    @Override
    public Pair<CustomerModel, CustomCustomerLocModel> getCustomerByEmail(String email) {
        Pair<CustomerModel, CustomCustomerLocModel> customerByEmail = customCustomerDao.findCustomerByEmail(email);
        return customerByEmail;
    }
}
