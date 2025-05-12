package com.sample.module.core.product.impl;

import com.sample.module.core.dto.CustomerDTO;
import com.sample.module.core.dto.ProductDTO;
import com.sample.module.core.model.DownloadUrlPropsModel;
import com.sample.module.core.product.ProductModelService;
import com.sample.module.core.product.dao.ProductDao;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("productModelService")
public class DefaultProductModelService implements ProductModelService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultProductModelService.class);

    @Resource(name = "customProductDao")
    private ProductDao customProductDao;
    @Autowired
    private ProductDao productDao;


    @Override
    public ProductModel getProductByCode(String code) {
        return customProductDao.findProductByCode(code);
    }

    @Override
    public List<ProductModel> getProductsForCatalog(String catalogId, String catalogVersion) {
        return customProductDao.findProductsForCatalog(catalogId, catalogVersion);
    }

    @Override
    public List<ProductData> setExpressDeliveryEligibilityPLP(List<ProductData> productDataList, CustomerDTO customerDTO) {
        List<ProductData> finalProductDataList = new ArrayList<>();
        for (ProductData productData : productDataList) {
            if (productData != null && customerDTO != null) {
                if (customerDTO.isExpressDelivery()) {
                    finalProductDataList.add(productData);
                } else {
                    productData.setExpressDeliveryEligibility(false);
                    finalProductDataList.add(productData);
                }
            } else {
                LOG.warn("Product or Customer DTO is null");
            }

        }
        return finalProductDataList;
    }

    @Override
    public DownloadUrlPropsModel getDownloadUrlPropsModel(String token) {
        return productDao.getDownloadUrlPropsModel(token);
    }

    @Override
    public ProductData setExpressDeliveryEligibility(ProductData productData, CustomerDTO customerDTO) {
        if (productData != null && customerDTO != null) {
            if (customerDTO.isExpressDelivery()) {
                return productData;
            } else {
                productData.setExpressDeliveryEligibility(false);
            }
        } else {
            LOG.warn("Product or Customer DTO is null");
        }
        return productData;
    }
}
