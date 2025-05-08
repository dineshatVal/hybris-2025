package com.sample.module.facades.populators;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;

public class ProductExpressDeliveryPopulator implements Populator<ProductModel, ProductData>
{

    @Override
    public void populate(ProductModel productModel, ProductData productData) throws de.hybris.platform.servicelayer.dto.converter.ConversionException {
        productData.setDescription(productModel.getDescription());
        productData.setExpressDeliveryEligibility(productModel.getExpressDeliveryEligibility());
    }
}
