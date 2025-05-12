package com.sample.module.facades.impl;

import com.custom.occ.dto.CustomProductWsDTO;
import com.sample.module.core.model.DownloadUrlPropsModel;
import com.sample.module.core.product.ProductModelService;
import com.sample.module.facades.DigitalFacade;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.model.ModelService;
import org.apache.commons.lang.time.DateUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

public class DefaultDigitalFacade implements DigitalFacade{

    @Resource(name = "modelService")
    private ModelService modelService;

    @Resource(name = "productModelService")
    private ProductModelService productModelService;

    /*@Resource(name = "digitalProductUpdatePopulator")
    private Populator<CustomProductWsDTO, ProductModel> digitalProductUpdatePopulator;*/

    @Resource(name = "productUpdateExpressFlagPopulator")
    private Populator<CustomProductWsDTO, ProductModel> productUpdateExpressFlagPopulator;


    @Override
    public String generateDownloadLink(String code, String email) {
        ProductModel productByCode = productModelService.getProductByCode(code);

        DownloadUrlPropsModel link = modelService.create(DownloadUrlPropsModel.class);
        link.setDownloadToken(UUID.randomUUID().toString());
        link.setValidUntil(DateUtils.addDays(new Date(), 7));

        link.setProduct(productByCode);
        link.setUserEmail(email);
        modelService.save(link);
        return link.getDownloadToken();
    }

    @Override
    public String getDownloadAccess(String downloadToken) throws Exception {
        DownloadUrlPropsModel link = findDownloadLinkByToken(downloadToken);

        if (link == null || new Date().after(link.getValidUntil())) {
            throw new Exception("Download link is expired or has reached its limit.");
        }

        ProductModel productModel = link.getProduct();
        String downloadUrl = productModel.getDownloadUrl();

        //String fileUrl = link.getProduct().getDownloadUrl();
        return downloadUrl;
    }

    @Override
    public String updateDigitalProduct(CustomProductWsDTO customProductWsDTO) {
        ProductModel model = productModelService.getProductByCode(customProductWsDTO.getCode());
        //digitalProductUpdatePopulator.populate(customProductWsDTO, model);
        productUpdateExpressFlagPopulator.populate(customProductWsDTO, model);
        modelService.save(model);
        return model.getDownloadUrl();
    }

    private DownloadUrlPropsModel findDownloadLinkByToken(String token) {
        return productModelService.getDownloadUrlPropsModel(token);
    }
}
