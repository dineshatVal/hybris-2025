package com.sample.module.core.product.dao;

import com.sample.module.core.model.DigitalDownloadTrackerModel;
import com.sample.module.core.model.DownloadUrlPropsModel;
import de.hybris.platform.core.model.product.ProductModel;

import java.util.List;

public interface ProductDao {
    ProductModel findProductByCode(String code);
    List<ProductModel> findProductsForCatalog(String catalogId, String catalogVersion);

    DownloadUrlPropsModel getDownloadUrlPropsModel(String token);

    DigitalDownloadTrackerModel getDigitalDownloadTrackerModel(String orderNum, String code);

    }