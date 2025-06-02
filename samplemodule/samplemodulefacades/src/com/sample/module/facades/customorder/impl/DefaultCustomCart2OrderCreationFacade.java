package com.sample.module.facades.customorder.impl;

import com.sample.module.core.customorder.CustomCart2OrderCreationService;
import com.sample.module.core.dto.DummyOrderRequestDTO;
import com.sample.module.facades.DigitalFacade;
import com.sample.module.facades.customorder.CustomCart2OrderCreationFacade;
import com.sample.module.facades.dto.CustomOrderResult;
import com.sample.module.facades.dto.ResponseDTO;
import com.sample.module.facades.populators.OrderDataToDTOPopulator;
import de.hybris.platform.core.model.order.OrderModel;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultCustomCart2OrderCreationFacade implements CustomCart2OrderCreationFacade {

    @Resource(name = "cart2orderconversionservice")
    private CustomCart2OrderCreationService cart2OrderConversionService;

    @Resource
    private DigitalFacade digitalFacade;

    @Resource(name = "customorderdatapopulator")
    private OrderDataToDTOPopulator customOrderDataPopulator;

    @Override
    public ResponseDTO createCustomOrderFromCart(String userId, List<DummyOrderRequestDTO.ProductEntry> productEntryList) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            OrderModel customOrderFromCart = cart2OrderConversionService.createCustomOrderFromCart(userId, productEntryList);

            //add download url params
            for (DummyOrderRequestDTO.ProductEntry productEntry : productEntryList) {
                //digitalFacade.generateDownloadLink(customOrderFromCart.getCode(), productEntry.getProductCode(), userId);
                digitalFacade.generateDownloadLinkV1(customOrderFromCart.getCode(), productEntry.getProductCode(), userId);
            }

            String concatenatedProductCodes = productEntryList
                    .stream()
                    .map(DummyOrderRequestDTO.ProductEntry::getProductCode)
                    .collect(Collectors.joining(","));

            CustomOrderResult customOrderResult = new CustomOrderResult();
            responseDTO.setStatus("Success");
            responseDTO.setMessage("Order placed for products :[" + concatenatedProductCodes + "] in order: "+customOrderFromCart.getCode());
            customOrderDataPopulator.populate(customOrderFromCart,customOrderResult);
            //responseDTO.setData(customOrderResult);
            responseDTO.setOrderResult(customOrderResult);
            return responseDTO;

            //return customOrderFromCart;
        } catch (Exception e) {
            responseDTO.setStatus("Error");
            responseDTO.setMessage("Error creating dummy order: " + e.getMessage());
            return responseDTO;
        }
    }
}
