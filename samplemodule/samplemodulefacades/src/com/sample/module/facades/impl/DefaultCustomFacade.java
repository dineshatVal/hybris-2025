package com.sample.module.facades.impl;

import com.custom.occ.dto.CustomProductWsDTO;
import com.sample.module.core.customer.CustomerModelService;
import com.sample.module.core.dto.CustomerDTO;
import com.sample.module.core.dto.ProductDTO;
import com.sample.module.core.model.CustomCustomerLocModel;
import com.sample.module.core.product.ProductModelService;
import com.sample.module.facades.CustomFacade;
import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.ProductSearchFacade;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.ProductCategorySearchPageData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.model.ModelService;
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
    private ProductSearchFacade<ProductData> productSearchFacade;

    @Autowired
    private CustomerModelService customerModelService;

    @Resource(name = "productConverter")
    private de.hybris.platform.servicelayer.dto.converter.Converter<ProductModel, ProductData> productConverter;

    @Resource(name = "productCusDTOConverter")
    private de.hybris.platform.servicelayer.dto.converter.Converter<ProductData, ProductDTO> productCusDTOConverter;

    @Autowired
    @Qualifier("productDTOConverter")
    private Converter<ProductModel, ProductDTO> productDTOConverter;

    @Resource(name = "modelService")
    private ModelService modelService;

    @Resource(name = "productUpdateExpressFlagPopulator")
    private Populator<CustomProductWsDTO, ProductModel> productUpdateExpressFlagPopulator;

    /*@Override
    public ProductDTO getProductByCode(String code, CustomerDTO customerDTO) {
        ProductModel productModel = productModelService.getProductByCode(code);
        ProductDTO productDTOConverted = productDTOConverter.convert(productModel);
        return productModelService.setExpressDeliveryEligibility(productDTOConverted,customerDTO);
    }*/

    @Override
    public CustomerDTO getCustomerByEmail(String email) {
        Pair<CustomerModel, CustomCustomerLocModel> customerByEmail = customerModelService.getCustomerByEmail(email);
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setEmail(customerByEmail.getLeft().getContactEmail());
        customerDTO.setExpressDelivery(customerByEmail.getRight().getExpressDelivery());
        return customerDTO;
    }

    /*@Override
    public List<ProductDTO> getProductsForCatalog(String catalogId, String catalogVersion, CustomerDTO customerDTO) {
        List<ProductModel> products = productModelService.getProductsForCatalog(catalogId, catalogVersion);
        List<ProductDTO> productDTOList = products.stream()
                .map(productDTOConverter::convert)
                .collect(Collectors.toList());
        return productModelService.setExpressDeliveryEligibilityPLP(productDTOList,customerDTO);
    }*/

    @Override
    public ProductData getProductByCodeCus(String productCode, CustomerDTO customerDTO) {
        ProductModel productByCode = productModelService.getProductByCode(productCode);
        ProductData productData = productConverter.convert(productByCode);

        ProductData productDataConverted = productModelService.setExpressDeliveryEligibility(productData, customerDTO);
        return productDataConverted;
    }

    @Override
    public List<ProductData> getProductsForCatalogCus(String catalogId, String catalogVersion, CustomerDTO customerDTO) {
        List<ProductModel> products = productModelService.getProductsForCatalog(catalogId, catalogVersion);
        List<ProductData> productDataList = products.stream()
                .map(productConverter::convert)
                .collect(Collectors.toList());

        List<ProductData> productDataLis = productModelService.setExpressDeliveryEligibilityPLP(productDataList, customerDTO);
        return productDataLis;
    }

    @Override
    public List<ProductData> getProductsForCategory(String categoryCode, CustomerDTO customerDTO) {
        ProductCategorySearchPageData<SearchStateData, ProductData, CategoryData> res =productSearchFacade.categorySearch(categoryCode);
        List<ProductData> productList = res.getResults();

        List<ProductData> productDataLis = productModelService.setExpressDeliveryEligibilityPLP(productList, customerDTO);

        return productDataLis;
    }

    /*@Override
    public String updateProductFromScratch(ProductData data) {
        ProductModel model = modelService.create(ProductModel.class);
        productUpdateExpressFlagPopulator.populate(data, model);
        modelService.save(model);
        return model.getCode();
    }*/

    @Override
    public String updateExistingProduct(CustomProductWsDTO data) {
        ProductModel model = productModelService.getProductByCode(data.getCode());
        productUpdateExpressFlagPopulator.populate(data, model);
        modelService.save(model);
        return model.getCode();
    }
}