package com.sample.module.core.product.impl;

import com.sample.module.core.dto.CustomerDTO;
import com.sample.module.core.dto.ProductDTO;
import com.sample.module.core.product.ProductModelService;
import com.sample.module.core.product.dao.ProductDao;
import de.hybris.platform.core.model.product.ProductModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("productModelService")
public class DefaultProductModelService implements ProductModelService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultProductModelService.class);

    @Resource(name = "customProductDao")
    private ProductDao customProductDao;


    @Override
    public ProductModel getProductByCode(String code) {
        return customProductDao.findProductByCode(code);
    }

    @Override
    public List<ProductModel> getProductsForCatalog(String catalogId, String catalogVersion) {
        return customProductDao.findProductsForCatalog(catalogId, catalogVersion);
    }

    @Override
    public List<ProductDTO> setExpressDeliveryEligibilityPLP(List<ProductDTO> productDTOList, CustomerDTO customerDTO) {
        List<ProductDTO> finalProductDTOList = new ArrayList<>();
        for (ProductDTO productDTO : productDTOList) {
            if (productDTO != null && customerDTO != null) {
                if (customerDTO.isExpressDelivery()) {
                    finalProductDTOList.add(productDTO);
                } else {
                    productDTO.setExpressDeliveryEligibility(false);
                    finalProductDTOList.add(productDTO);
                }
            } else {
                LOG.warn("Product or Customer DTO is null");
            }

        }
        return finalProductDTOList;
    }

    @Override
    public ProductDTO setExpressDeliveryEligibility(ProductDTO productDTO, CustomerDTO customerDTO) {
        if (productDTO != null && customerDTO != null) {
            if (customerDTO.isExpressDelivery()) {
                return productDTO;
            } else {
                productDTO.setExpressDeliveryEligibility(false);
            }
        } else {
            LOG.warn("Product or Customer DTO is null");
        }
        return productDTO;
    }
}
