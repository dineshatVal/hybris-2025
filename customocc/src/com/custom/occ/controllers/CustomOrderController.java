/*
 * Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.custom.occ.controllers;

import com.sample.module.core.dto.DummyOrderRequestDTO;
import com.sample.module.facades.customorder.CustomOrderCreationFacade;
import de.hybris.platform.commerceservices.request.mapping.annotation.ApiVersion;
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

@Controller
@ApiVersion("v2")
@Api(tags = "Custom Order Controller")
@RequestMapping(value = "/{baseSiteId}")
public class CustomOrderController
{
    private static final Logger LOG = LoggerFactory.getLogger(CustomOrderController.class);

    @Resource(name = "customOrderCreationFacade")
    private CustomOrderCreationFacade customOrderCreationFacade;

    @Secured("ROLE_CLIENT")
    @GetMapping("/hello-order")
    public ResponseEntity<String> sayHelloOrder() {
        return ResponseEntity.ok("Hello from dummy order placement controller!");
    }

    @Secured("ROLE_CLIENT")
    @PostMapping("/createCustomOrder")
    public ResponseEntity<String> createCustomOrder(@RequestBody DummyOrderRequestDTO dummyOrderRequestDTO) {

        String customOrder = customOrderCreationFacade.createCustomOrder(dummyOrderRequestDTO.getUserId(), dummyOrderRequestDTO.getProductEntries());
        return ResponseEntity.ok(customOrder);
    }

}
