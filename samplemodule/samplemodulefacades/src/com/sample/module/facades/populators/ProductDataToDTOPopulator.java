package com.sample.module.facades.populators;

import de.hybris.platform.commercefacades.product.data.ProductData;
import com.sample.module.core.dto.ProductDTO;
import de.hybris.platform.converters.Populator;

public class ProductDataToDTOPopulator implements Populator<ProductData, ProductDTO> {
    @Override
    public void populate(ProductData source, ProductDTO target) {
        target.setCode(source.getCode());
        target.setName(source.getName());
        target.setDescription(source.getDescription());

        Boolean eligible = source.getExpressDeliveryEligibility();
        target.setExpressDeliveryEligibility(eligible != null && eligible);
    }
}
