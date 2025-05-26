/*
 * Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.custom.occ.controllers;

import com.sample.module.core.dto.DummyOrderRequestDTO;
import com.sample.module.facades.customorder.CustomCart2OrderCreationFacade;
import com.sample.module.facades.customorder.CustomOrderCreationFacade;
import de.hybris.platform.commerceservices.request.mapping.annotation.ApiVersion;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.order.events.SubmitOrderEvent;
import de.hybris.platform.servicelayer.event.EventService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.stream.Collectors;

@Controller
@ApiVersion("v2")
@Api(tags = "Custom Cart2Order Controller")
@RequestMapping(value = "/{baseSiteId}")
public class CustomCartToOrderConversionController
{
    private static final Logger LOG = LoggerFactory.getLogger(CustomCartToOrderConversionController.class);

    @Resource(name = "eventService")
    private EventService eventService;

    @Resource(name = "customcart2orderfacade")
    private CustomCart2OrderCreationFacade customcart2orderfacade;

    @Secured("ROLE_CLIENT")
    @GetMapping("/hello-cart2order")
    public ResponseEntity<String> sayHelloOrder() {
        return ResponseEntity.ok("Hello from dummy cart2order placement controller!");
    }

    @Secured("ROLE_CLIENT")
    @PostMapping("/createCustomOrderFromCart")
    public ResponseEntity<String> createCustomOrderFromCart(@RequestBody DummyOrderRequestDTO dummyOrderRequestDTO) {

        OrderModel customOrder = customcart2orderfacade.createCustomOrderFromCart(dummyOrderRequestDTO.getUserId(), dummyOrderRequestDTO.getProductEntries());
        String concatenatedProductCodes = dummyOrderRequestDTO.getProductEntries()
                .stream()
                .map(DummyOrderRequestDTO.ProductEntry::getProductCode)
                .collect(Collectors.joining(","));
        return ResponseEntity.ok("Order placed for products :[" + concatenatedProductCodes + "] in order: "+customOrder.getCode());
    }

}
