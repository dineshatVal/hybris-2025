package com.sample.module.facades.populators;

import com.custom.occ.dto.CustomProductWsDTO;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.util.Objects;

public class UpdateProductFlagsPopulator implements Populator<CustomProductWsDTO, ProductModel> {

    @Override
    public void populate(CustomProductWsDTO source, ProductModel target) throws ConversionException {
        if(Objects.nonNull(source.getExpressDeliveryEligibility())){
            target.setExpressDeliveryEligibility(source.getExpressDeliveryEligibility());
        }

        /*if(Objects.nonNull(source.getIsDigitalProduct())){
            target.setIsDigitalProduct(source.getIsDigitalProduct());
        }*/

        if(source.getDownloadUrl() != null){
            target.setDownloadUrl(source.getDownloadUrl());
        }

    }
}
