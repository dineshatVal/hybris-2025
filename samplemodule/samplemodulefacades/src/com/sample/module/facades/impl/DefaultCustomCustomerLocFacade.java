package com.sample.module.facades.impl;

import com.sample.module.core.customer.CustomerLocModelService;
import com.sample.module.core.model.CustomCustomerLocModel;
import com.sample.module.facades.CustomCustomerLocFacade;
import com.sample.module.facades.customcustomerloc.data.CustomCustomLocData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;

public class DefaultCustomCustomerLocFacade implements CustomCustomerLocFacade {

    @Resource(name = "customCustomerLocModelService")
    private CustomerLocModelService customCustomerLocModelService;

    @Resource(name = "modelService")
    private ModelService modelService;

    @Resource(name = "customCustomerLocPopulator")
    private Populator<CustomCustomLocData, CustomCustomerLocModel> customCustomerLocPopulator;

    @Override
    public String registerLocation(CustomCustomLocData data) {
        CustomCustomerLocModel model = modelService.create(CustomCustomerLocModel.class);
        customCustomerLocPopulator.populate(data, model);
        int nextUid = customCustomerLocModelService.createCustomCustomerLocUid();
        model.setUid(String.valueOf(nextUid));
        modelService.save(model);
        return model.getUid();
    }
}
