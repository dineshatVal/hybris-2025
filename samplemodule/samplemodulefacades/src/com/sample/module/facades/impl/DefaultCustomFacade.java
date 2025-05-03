package com.sample.module.facades.impl;

import com.sample.module.core.customer.CustomerModelService;
import com.sample.module.core.dto.CustomerDTO;
import com.sample.module.core.dto.ProductDTO;
import com.sample.module.core.model.CustomCustomerLocModel;
import com.sample.module.core.product.ProductModelService;
import com.sample.module.facades.CustomFacade;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultCustomFacade implements CustomFacade {

    @Autowired
    private ProductModelService productModelService;

    @Autowired
    private CustomerModelService customerModelService;

    @Resource(name = "productConverter")
    private de.hybris.platform.servicelayer.dto.converter.Converter<ProductModel, ProductData> productConverter;

    @Resource(name = "productCusDTOConverter")
    private de.hybris.platform.servicelayer.dto.converter.Converter<ProductData, ProductDTO> productCusDTOConverter;

    @Autowired
    @Qualifier("productDTOConverter")
    private Converter<ProductModel, ProductDTO> productDTOConverter;

    @Override
    public ProductDTO getProductByCode(String code, CustomerDTO customerDTO) {
        ProductModel productModel = productModelService.getProductByCode(code);
        ProductDTO productDTOConverted = productDTOConverter.convert(productModel);
        return productModelService.setExpressDeliveryEligibility(productDTOConverted,customerDTO);
    }

    @Override
    public CustomerDTO getCustomerByEmail(String email) {
        Pair<CustomerModel, CustomCustomerLocModel> customerByEmail = customerModelService.getCustomerByEmail(email);
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setEmail(customerByEmail.getLeft().getContactEmail());
        customerDTO.setExpressDelivery(customerByEmail.getRight().getExpressDelivery());
        return customerDTO;
    }

    @Override
    public List<ProductDTO> getProductsForCatalog(String catalogId, String catalogVersion, CustomerDTO customerDTO) {
        List<ProductModel> products = productModelService.getProductsForCatalog(catalogId, catalogVersion);
        List<ProductDTO> productDTOList = products.stream()
                .map(productDTOConverter::convert)
                .collect(Collectors.toList());
        return productModelService.setExpressDeliveryEligibilityPLP(productDTOList,customerDTO);
    }

    @Override
    public ProductDTO getProductByCodeCus(String productCode, CustomerDTO customerDTO) {
        ProductModel productByCode = productModelService.getProductByCode(productCode);
        ProductData productData = productConverter.convert(productByCode);
        ProductDTO productDTO = productCusDTOConverter.convert(productData);
        return productModelService.setExpressDeliveryEligibility(productDTO,customerDTO);

       // return productDTO;
    }

    @Override
    public List<ProductDTO> getProductsForCatalogCus(String catalogId, String catalogVersion, CustomerDTO customerDTO) {
        List<ProductModel> products = productModelService.getProductsForCatalog(catalogId, catalogVersion);
        List<ProductData> productDTOList = products.stream()
                .map(productConverter::convert)
                .collect(Collectors.toList());
        List<ProductDTO> productDTOS = productDTOList.stream()
                .map(productCusDTOConverter::convert)
                .collect(Collectors.toList());
        return productModelService.setExpressDeliveryEligibilityPLP(productDTOS,customerDTO);

        //return productDTOS;
    }
}