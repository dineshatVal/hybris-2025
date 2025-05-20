/*
 * Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.custom.occ.controllers;

import com.sample.module.core.dto.DummyOrderRequestDTO;
import com.sample.module.facades.DigitalFacade;
import de.hybris.platform.commerceservices.request.mapping.annotation.ApiVersion;
import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

@Controller
@ApiVersion("v2")
@Api(tags = "Custom Order Controller")
@RequestMapping(value = "/{baseSiteId}")
public class CustomOrderController
{
    private static final Logger LOG = LoggerFactory.getLogger(CustomOrderController.class);

    @Resource
    private ModelService modelService;

    @Resource
    private UserService userService;

    @Resource
    private ProductService productService;

    @Resource(name = "digitalFacade")
    private DigitalFacade digitalFacade;


    @GetMapping("/hello-order")
    public ResponseEntity<String> sayHelloOrder() {
        return ResponseEntity.ok("Hello from dummy order placement controller!");
    }

    @PostMapping("/createCustomOrder")
    public ResponseEntity<String> createCustomOrder(@RequestBody DummyOrderRequestDTO dummyOrderRequestDTO) {

        try {
            // Load user and product
            UserModel user = userService.getUserForUID(dummyOrderRequestDTO.getUserId());
            ProductModel product = productService.getProductForCode(dummyOrderRequestDTO.getProductCode());

            // Create order
            OrderModel order = modelService.create(OrderModel.class);
            order.setUser(user);
            order.setDate(new Date());
            order.setCode("DUMMY-" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6).toUpperCase());
            order.setCurrency(product.getCatalogVersion().getCatalog().getDefaultCurrency()); // or set default currency
            modelService.save(order);

            // Create order entry
            OrderEntryModel entry = modelService.create(OrderEntryModel.class);
            entry.setProduct(product);
            entry.setQuantity(dummyOrderRequestDTO.getQuantity());
            entry.setOrder(order);
            entry.setUnit(product.getUnit()); // Be sure the product has a unit set

            // Add entry to order
            order.setEntries(Collections.singletonList(entry));
            modelService.save(entry);
            modelService.save(order);

            //add download url params
            digitalFacade.generateDownloadLink(order.getCode(), product.getCode(), user.getUid());

            return ResponseEntity.ok("Order created with code: " + order.getCode());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error creating dummy order: " + e.getMessage());
        }
    }

}
