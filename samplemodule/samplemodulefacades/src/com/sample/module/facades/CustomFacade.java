package com.sample.module.facades;

import com.sample.module.core.dto.CustomerDTO;
import com.sample.module.core.dto.ProductDTO;

import java.util.List;

public interface CustomFacade {
    ProductDTO getProductByCode(String productCode, CustomerDTO customerDTO);
    CustomerDTO getCustomerByEmail(String email);

    List<ProductDTO> getProductsForCatalog(String catalogId, String catalogVersion, CustomerDTO customerDTO);

    ProductDTO getProductByCodeCus(String productCode, CustomerDTO customerDTO);
    List<ProductDTO> getProductsForCatalogCus(String catalogId, String catalogVersion, CustomerDTO customerDTO);

}
