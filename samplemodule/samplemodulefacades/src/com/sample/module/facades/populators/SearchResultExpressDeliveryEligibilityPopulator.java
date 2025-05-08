package com.sample.module.facades.populators;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class SearchResultExpressDeliveryEligibilityPopulator implements Populator<SearchResultValueData, ProductData> {
    @Override
    public void populate(SearchResultValueData source, ProductData target) throws ConversionException {
        target.setExpressDeliveryEligibility(this.<Boolean> getValue(source, "expressDeliveryEligibility"));
    }

    protected <T> T getValue(final SearchResultValueData source, final String propertyName)
    {
        if (source.getValues() == null)
        {
            return null;
        }
        return (T) source.getValues().get(propertyName);
    }
}
