/*
 * Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.custom.occ.controllers;

import com.sample.module.core.dto.DummyOrderRequestDTO;
import com.sample.module.facades.customorder.CustomCart2OrderCreationFacade;
import com.sample.module.facades.dto.ResponseDTO;
import de.hybris.platform.commerceservices.request.mapping.annotation.ApiVersion;
import de.hybris.platform.servicelayer.event.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    public ResponseEntity<String> sayHelloCartToOrder(@ApiParam(value = "Base site identifier", required = true) @PathVariable final String baseSiteId) {
        return ResponseEntity.ok("Hello from dummy cart2order placement controller!");
    }

    @Secured("ROLE_CLIENT")
    @PostMapping("/createCustomOrderFromCart")
    public ResponseEntity<ResponseDTO> createCustomOrderFromCart(@RequestBody DummyOrderRequestDTO dummyOrderRequestDTO) {

        ResponseDTO customResponseDto = customcart2orderfacade.createCustomOrderFromCart(dummyOrderRequestDTO.getUserId(), dummyOrderRequestDTO.getProductEntries());
        return ResponseEntity.ok(customResponseDto);
    }

}
