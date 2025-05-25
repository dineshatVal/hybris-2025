package com.sample.module.facades.customorder.impl;

import com.sample.module.core.customorder.CustomCart2OrderCreationService;
import com.sample.module.core.dto.DummyOrderRequestDTO;
import com.sample.module.facades.DigitalFacade;
import com.sample.module.facades.customorder.CustomCart2OrderCreationFacade;
import de.hybris.platform.core.model.order.OrderModel;

import javax.annotation.Resource;
import java.util.List;

public class DefaultCustomCart2OrderCreationFacade implements CustomCart2OrderCreationFacade {

    @Resource(name = "cart2orderconversionservice")
    private CustomCart2OrderCreationService cart2OrderConversionService;

    @Resource
    private DigitalFacade digitalFacade;

    @Override
    public OrderModel createCustomOrderFromCart(String userId, List<DummyOrderRequestDTO.ProductEntry> productEntryList) {
        try {
            OrderModel customOrderFromCart = cart2OrderConversionService.createCustomOrderFromCart(userId, productEntryList);

            //add download url params
            for (DummyOrderRequestDTO.ProductEntry productEntry : productEntryList) {
                digitalFacade.generateDownloadLink(customOrderFromCart.getCode(), productEntry.getProductCode(), userId);
            }

            return customOrderFromCart;
        } catch (Exception e) {
            throw e;
        }

    }
}
