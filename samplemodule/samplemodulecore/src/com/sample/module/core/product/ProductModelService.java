package com.sample.module.core.product;

import com.sample.module.core.dto.CustomerDTO;
import com.sample.module.core.dto.ProductDTO;
import com.sample.module.core.model.DownloadUrlPropsModel;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;

import java.util.List;

public interface ProductModelService {
    ProductModel getProductByCode(String code);
    ProductData setExpressDeliveryEligibility(ProductData productData, CustomerDTO customerDTO);
    List<ProductModel> getProductsForCatalog(String catalogId, String catalogVersion);
    List<ProductData> setExpressDeliveryEligibilityPLP(List<ProductData> productDataList, CustomerDTO customerDTO);

    DownloadUrlPropsModel getDownloadUrlPropsModel(String token);
}
