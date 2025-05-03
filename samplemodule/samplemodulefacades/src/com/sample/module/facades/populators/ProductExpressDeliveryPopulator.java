package com.sample.module.facades.populators;

import com.sample.module.core.dto.ProductDTO;
import de.hybris.platform.commercefacades.product.converters.populator.AbstractProductPopulator;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.ConversionException;

public class ProductExpressDeliveryPopulator<SOURCE extends ProductModel, TARGET extends ProductData>
        implements Populator<ProductModel, ProductData>
{
    /*@Override
    public void populate(final SOURCE productModel, final TARGET productData) throws ConversionException
    {
        *//*if (StringUtils.isNotBlank(productModel.getExpressDeliveryEligibility()))
        {
            productData.setNotification(productModel.getNotification().trim());
        }*//*

        productData.setExpressDeliveryEligibility(productModel.getExpressDeliveryEligibility());
    }*/

    @Override
    public void populate(ProductModel productModel, ProductData productData) throws de.hybris.platform.servicelayer.dto.converter.ConversionException {
        productData.setDescription(productModel.getDescription());
        productData.setExpressDeliveryEligibility(productModel.getExpressDeliveryEligibility());
    }
}
