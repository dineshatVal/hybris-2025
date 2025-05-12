package com.sample.module.facades;

import com.custom.occ.dto.CustomProductWsDTO;
import com.sample.module.core.dto.CustomerDTO;
import de.hybris.platform.commercefacades.product.data.ProductData;

import java.util.List;

public interface CustomFacade {
   // ProductDTO getProductByCode(String productCode, CustomerDTO customerDTO);
    CustomerDTO getCustomerByEmail(String email);

   // List<ProductDTO> getProductsForCatalog(String catalogId, String catalogVersion, CustomerDTO customerDTO);

    ProductData getProductByCodeCus(String productCode, CustomerDTO customerDTO);
    List<ProductData> getProductsForCatalogCus(String catalogId, String catalogVersion, CustomerDTO customerDTO);

    List<ProductData> getProductsForCategory(String categoryCode, CustomerDTO customerDTO);

    String updateExistingProduct(CustomProductWsDTO data);


}
