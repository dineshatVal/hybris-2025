/*
 * Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.custom.occ.controllers;

import com.custom.occ.dto.CustomProductWsDTO;
import com.sample.module.core.dto.CustomerDTO;
import com.sample.module.facades.CustomCustomerLocFacade;
import com.sample.module.facades.CustomFacade;
import com.sample.module.facades.customcustomerloc.data.CustomCustomLocData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.request.mapping.annotation.ApiVersion;
import de.hybris.platform.webservicescommons.mapping.DataMapper;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @Autowired
    private CustomCustomerLocFacade customCustomerLocFacade;

    @Resource(name = "dataMapper")
    private DataMapper dataMapper;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from my first OCC extension!");
    }

    /*@Deprecated
    @GetMapping("/{productCode}")
    public ResponseEntity<ProductDTO> getProductByCode(@PathVariable String productCode, @RequestParam String email) {
        CustomerDTO customerDTO = customFacade.getCustomerByEmail(email);

        ProductDTO finalProductDTO = customFacade.getProductByCode(productCode, customerDTO);

        return ResponseEntity.ok(finalProductDTO);
    }*/

    /*@Deprecated
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
    }*/

    @GetMapping("/{productCode}")
    public ResponseEntity<CustomProductWsDTO> getProductByCodeCus(@PathVariable String productCode, @RequestParam String email) {
        CustomerDTO customerDTO = customFacade.getCustomerByEmail(email);

        ProductData productByCodeCus = customFacade.getProductByCodeCus(productCode, customerDTO);
        CustomProductWsDTO customProductWsDTO = dataMapper.map(productByCodeCus, CustomProductWsDTO.class);
        return ResponseEntity.ok(customProductWsDTO);
    }

    @GetMapping("/catalog-products")
    public ResponseEntity<List<CustomProductWsDTO>> getProductsForCatalogCus(@RequestParam String catalogId,
                                                                  @RequestParam String catalogVersion, @RequestParam String email) {
        CustomerDTO customerDTO = customFacade.getCustomerByEmail(email);

        try {
            List<CustomProductWsDTO> customProductWsDTOList =  new java.util.ArrayList<>();
            List<ProductData> productsForCatalog = customFacade.getProductsForCatalogCus(catalogId, catalogVersion, customerDTO);
            for (ProductData productData : productsForCatalog) {
                CustomProductWsDTO customProductWsDTO = dataMapper.map(productData, CustomProductWsDTO.class);
                customProductWsDTOList.add(customProductWsDTO);
            }
            if (productsForCatalog == null || productsForCatalog.isEmpty()) {
                LOG.warn("No products found for catalogId: {} and catalogVersion: {}", catalogId, catalogVersion);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(customProductWsDTOList);
        } catch (Exception e) {
            LOG.error("Error fetching products for catalogId: {} and catalogVersion: {}", catalogId, catalogVersion, e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/category-products")
    public ResponseEntity<List<CustomProductWsDTO>> getProductsForCategory(@RequestParam String categoryCode,
                                                                       @RequestParam String email) {
        CustomerDTO customerDTO = customFacade.getCustomerByEmail(email);

        try {
            List<CustomProductWsDTO> customProductWsDTOList =  new java.util.ArrayList<>();
            List<ProductData> productDataList = customFacade.getProductsForCategory(categoryCode, customerDTO);
            for (ProductData productData : productDataList) {
                CustomProductWsDTO productWsDTO = dataMapper.map(productData, CustomProductWsDTO.class);
                customProductWsDTOList.add(productWsDTO);
            }
            if (productDataList == null || productDataList.isEmpty()) {
                LOG.warn("No products found");
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(customProductWsDTOList);
        } catch (Exception e) {
            LOG.error("Error fetching products for categorycode: ", e);
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/addCustomerLoc")
    public ResponseEntity<String> createCustomCustomerLoc(@RequestBody CustomCustomLocData data) {
        return ResponseEntity.ok(customCustomerLocFacade.registerLocation(data));
    }

    @PostMapping("/updateProductExpFlagV1")
    public ResponseEntity<String> updateProductExpFlagV1(@RequestBody CustomProductWsDTO data) {
        return ResponseEntity.ok(customFacade.updateExistingProduct(data));
    }
}
