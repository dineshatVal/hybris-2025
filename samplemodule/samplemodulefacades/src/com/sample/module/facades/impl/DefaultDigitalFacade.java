package com.sample.module.facades.impl;

import com.custom.occ.dto.CustomProductWsDTO;
import com.sample.module.core.awsservice.S3PresignedUrlService;
import com.sample.module.core.digitalservice.DigitalProductService;
import com.sample.module.core.model.DownloadUrlPropsModel;
import com.sample.module.core.product.ProductModelService;
import com.sample.module.core.token.CustomTokenService;
import com.sample.module.facades.DigitalFacade;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.model.ModelService;
import org.apache.commons.lang.time.DateUtils;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Date;

public class DefaultDigitalFacade implements DigitalFacade{

    @Resource(name = "modelService")
    private ModelService modelService;

    @Resource(name = "productModelService")
    private ProductModelService productModelService;

    @Resource(name = "productUpdateExpressFlagPopulator")
    private Populator<CustomProductWsDTO, ProductModel> productUpdateExpressFlagPopulator;

    @Resource(name = "defaultDigitalProductService")
    private DigitalProductService defaultDigitalProductService;

    @Resource(name = "s3PresignedUrlService")
    private S3PresignedUrlService s3PresignedUrlService;

    @Resource(name = "defaultCustomTokenService")
    private CustomTokenService defaultCustomTokenService;

    private int maxDownload;
    private long signedUrlValidityPeriod;
    private String bucketName;

    @Override
    public String generateDownloadLink(String orderNum, String code, String email) {
        ProductModel productByCode = productModelService.getProductByCode(code);

        DownloadUrlPropsModel link = modelService.create(DownloadUrlPropsModel.class);
        link.setDownloadToken(defaultCustomTokenService.generateToken(orderNum,code,email));
        link.setValidUntil(DateUtils.addDays(new Date(), 7));

        link.setProduct(productByCode);
        link.setUserEmail(email);

        link.setMaxDownloads(maxDownload);
        link.setDownloadCount(0);

        modelService.save(link);
        modelService.refresh(link);
        return link.getDownloadToken();
    }

    /*@Override
    public String getDownloadAccess(String downloadToken, String email) throws Exception {
        DownloadUrlPropsModel link = findDownloadLinkByToken(downloadToken);

        if(!email.equalsIgnoreCase(link.getUserEmail())) {
            return "Download failed - Invalid user cedentials.";
            //throw new Exception("User details not verified. Please try again.");
        }
        if (link == null || new Date().after(link.getValidUntil())) {
            return "Download failed - Link expired.";
            //throw new Exception("Download link is expired or has reached its limit.");
        }
        if(link.getDownloadCount() >= link.getMaxDownloads()){
            return "Download failed - Download limit exceeded.";
            //throw new Exception("Download limit reached.");
        }

        ProductModel productModel = link.getProduct();
        String downloadUrl = productModel.getDownloadUrl();
        boolean downloadRequestStatus = defaultDigitalProductService.downloadRequest(downloadUrl);

        if (!downloadRequestStatus) {
            return "Download Failed";
        }
        link.setDownloadCount(link.getDownloadCount() + 1);
        modelService.save(link);
        modelService.refresh(link);
        return "Download Success";
    }*/

    @Override
    public String updateDigitalProduct(CustomProductWsDTO customProductWsDTO) {
        ProductModel model = productModelService.getProductByCode(customProductWsDTO.getCode());
        //digitalProductUpdatePopulator.populate(customProductWsDTO, model);
        productUpdateExpressFlagPopulator.populate(customProductWsDTO, model);
        modelService.save(model);
        return model.getDownloadUrl();
    }

    @Override
    public String getSecuredDownloadAccess(String orderNum, String code, String email) throws Exception {

        DownloadUrlPropsModel link = findDownloadLinkByToken(defaultCustomTokenService.generateToken(orderNum,code,email));

        if(!email.equalsIgnoreCase(link.getUserEmail())) {
            return "Download failed - Invalid user cedentials.";
            //throw new Exception("User details not verified. Please try again.");
        }
        if (link == null || new Date().after(link.getValidUntil())) {
            return "Download failed - Link expired.";
            //throw new Exception("Download link is expired or has reached its limit.");
        }
        if(link.getDownloadCount() >= link.getMaxDownloads()){
            return "Download failed - Download limit exceeded.";
            //throw new Exception("Download limit reached.");
        }

        ProductModel productModel = link.getProduct();
        String downloadUrl = productModel.getDownloadUrl();

        String presignedUrl = s3PresignedUrlService.generatePresignedUrl(bucketName, downloadUrl, Duration.ofMinutes(signedUrlValidityPeriod));


        boolean downloadRequestStatus = defaultDigitalProductService.downloadRequest(presignedUrl);

        if (!downloadRequestStatus) {
            return "Download Failed";
        }
        link.setDownloadCount(link.getDownloadCount() + 1);
        modelService.save(link);
        modelService.refresh(link);
        return "Download Success";
    }

    private DownloadUrlPropsModel findDownloadLinkByToken(String token) {
        return productModelService.getDownloadUrlPropsModel(token);
    }

    public void setMaxDownload(int maxDownload) {
        this.maxDownload = maxDownload;
    }

    public void setSignedUrlValidityPeriod(long signedUrlValidityPeriod) {
        this.signedUrlValidityPeriod = signedUrlValidityPeriod;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
