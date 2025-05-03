package com.sample.module.facades.populators;

import com.sample.module.core.dto.ProductDTO;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.converters.Populator;

public class ProductPopulator implements Populator<ProductModel, ProductDTO> {
    @Override
    public void populate(ProductModel source, ProductDTO target) {
        target.setCode(source.getCode());
        target.setName(source.getName());
        target.setDescription(source.getDescription());

        Boolean eligible = source.getExpressDeliveryEligibility();
        target.setExpressDeliveryEligibility(eligible != null && eligible);
    }
}
