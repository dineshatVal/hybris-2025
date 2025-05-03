package com.sample.module.core.customer;

import com.sample.module.core.model.CustomCustomerLocModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import org.apache.commons.lang3.tuple.Pair;

public interface CustomerModelService {
    Pair<CustomerModel, CustomCustomerLocModel> getCustomerByEmail(String email);
}
