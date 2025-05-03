/*
 * Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.custom.occ.controllers;

import com.sample.module.core.dto.CustomerDTO;
import com.sample.module.core.dto.ProductDTO;
import com.sample.module.facades.CustomFacade;
import de.hybris.platform.commerceservices.request.mapping.annotation.ApiVersion;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@ApiVersion("v2")
@Api(tags = "ExpressDelivery Controller")
@RequestMapping(value = "/{baseSiteId}")
public class CustomoccController
{
    private static final Logger LOG = LoggerFactory.getLogger(CustomoccController.class);

    @Autowired
    private CustomFacade customFacade;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from my first OCC extension!");
    }

    @Deprecated
    @GetMapping("/{productCode}")
    public ResponseEntity<ProductDTO> getProductByCode(@PathVariable String productCode, @RequestParam String email) {
        CustomerDTO customerDTO = customFacade.getCustomerByEmail(email);

        ProductDTO finalProductDTO = customFacade.getProductByCode(productCode, customerDTO);

        return ResponseEntity.ok(finalProductDTO);
    }

    @Deprecated
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getProductsForCatalog(@RequestParam String catalogId,
                        @RequestParam String catalogVersion, @RequestParam String email) {
        CustomerDTO customerDTO = customFacade.getCustomerByEmail(email);

        try {
            List<ProductDTO> productsForCatalog = customFacade.getProductsForCatalog(catalogId, catalogVersion, customerDTO);

            if (productsForCatalog == null || productsForCatalog.isEmpty()) {
                LOG.warn("No products found for catalogId: {} and catalogVersion: {}", catalogId, catalogVersion);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(productsForCatalog);
        } catch (Exception e) {
            LOG.error("Error fetching products for catalogId: {} and catalogVersion: {}", catalogId, catalogVersion, e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/new/{productCode}")
    public ResponseEntity<ProductDTO> getProductByCodeCus(@PathVariable String productCode, @RequestParam String email) {
        CustomerDTO customerDTO = customFacade.getCustomerByEmail(email);

        ProductDTO productByCodeCus = customFacade.getProductByCodeCus(productCode, customerDTO);
        return ResponseEntity.ok(productByCodeCus);
    }

    @GetMapping("/new/products")
    public ResponseEntity<List<ProductDTO>> getProductsForCatalogCus(@RequestParam String catalogId,
                                                                  @RequestParam String catalogVersion, @RequestParam String email) {
        CustomerDTO customerDTO = customFacade.getCustomerByEmail(email);

        try {
            List<ProductDTO> productsForCatalog = customFacade.getProductsForCatalogCus(catalogId, catalogVersion, customerDTO);

            if (productsForCatalog == null || productsForCatalog.isEmpty()) {
                LOG.warn("No products found for catalogId: {} and catalogVersion: {}", catalogId, catalogVersion);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(productsForCatalog);
        } catch (Exception e) {
            LOG.error("Error fetching products for catalogId: {} and catalogVersion: {}", catalogId, catalogVersion, e);
            return ResponseEntity.status(500).build();
        }
    }
}
