package com.sample.module.facades.populators;

import com.sample.module.core.model.CustomCustomerLocModel;
import com.sample.module.facades.customcustomerloc.data.CustomCustomLocData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;

public class CustomCustomerLocPopulator implements Populator<CustomCustomLocData, CustomCustomerLocModel> {

    private CommonI18NService commonI18NService;

    @Override
    public void populate(CustomCustomLocData source, CustomCustomerLocModel target) throws ConversionException {
        //target.setUid(source.getUid());
        target.setExpressDelivery(source.getExpressDelivery());

        CountryModel country = commonI18NService.getCountry(source.getCountryIsoCode());
        target.setCountry(country);

        if (country != null && source.getRegionIsoCodeShort() != null) {
            for (RegionModel region : country.getRegions()) {
                if (region.getIsocodeShort().equalsIgnoreCase(source.getRegionIsoCodeShort())) {
                    target.setRegion(region);
                    break;
                }
            }
        }
    }

    public void setCommonI18NService(CommonI18NService commonI18NService) {
        this.commonI18NService = commonI18NService;
    }
}
