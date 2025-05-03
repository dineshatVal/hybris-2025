package com.sample.module.core.product;

import com.sample.module.core.dto.CustomerDTO;
import com.sample.module.core.dto.ProductDTO;
import de.hybris.platform.core.model.product.ProductModel;

import java.util.List;

public interface ProductModelService {
    ProductModel getProductByCode(String code);
    ProductDTO setExpressDeliveryEligibility(ProductDTO productDTO, CustomerDTO customerDTO);
    List<ProductModel> getProductsForCatalog(String catalogId, String catalogVersion);
    List<ProductDTO> setExpressDeliveryEligibilityPLP(List<ProductDTO> productDTOList, CustomerDTO customerDTO);

}
