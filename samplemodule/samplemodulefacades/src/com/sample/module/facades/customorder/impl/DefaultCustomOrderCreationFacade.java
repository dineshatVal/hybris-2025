package com.sample.module.facades.customorder.impl;

import com.sample.module.core.customorder.CustomOrderCreationService;
import com.sample.module.core.dto.DummyOrderRequestDTO;
import com.sample.module.facades.DigitalFacade;
import com.sample.module.facades.customorder.CustomOrderCreationFacade;
import de.hybris.platform.core.model.order.OrderModel;

import javax.annotation.Resource;
import java.util.List;

public class DefaultCustomOrderCreationFacade implements CustomOrderCreationFacade {

    @Resource
    private DigitalFacade digitalFacade;


    @Resource(name = "customOrderCreationService")
    private CustomOrderCreationService customOrderCreationService;

    @Override
    public OrderModel createCustomOrder(String userId, List<DummyOrderRequestDTO.ProductEntry> productEntryList) {
        try {
            OrderModel order = customOrderCreationService.createCustomOrder(userId, productEntryList);
            //add download url params
            for (DummyOrderRequestDTO.ProductEntry productEntry : productEntryList) {
                digitalFacade.generateDownloadLink(order.getCode(), productEntry.getProductCode(), userId);
            }

            return order;
            //return "Order created with code: " + order.getCode();

        } catch (Exception e) {
            throw e;
            //return "Error creating dummy order: " + e.getMessage();
        }
    }
}
