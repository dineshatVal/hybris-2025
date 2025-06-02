package com.sample.module.facades.impl;

import com.custom.occ.dto.CustomProductWsDTO;
import com.sample.module.core.awsservice.S3PresignedUrlService;
import com.sample.module.core.digitalservice.DigitalProductService;
import com.sample.module.core.dto.CustomException;
import com.sample.module.core.model.DigitalDownloadTrackerModel;
import com.sample.module.core.model.DownloadUrlPropsModel;
import com.sample.module.core.product.ProductModelService;
import com.sample.module.core.token.CustomTokenService;
import com.sample.module.facades.DigitalFacade;
import com.sample.module.facades.dto.DownloadDataResult;
import com.sample.module.facades.dto.ResponseDTO;
import com.sample.module.facades.populators.DigitalDownloadDataToDTOPopulator;
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

    @Resource(name = "downloaddatapopulator")
    private DigitalDownloadDataToDTOPopulator downloaddatapopulator;

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

    @Override
    public void generateDownloadLinkV1(String orderNum, String code, String email) {
        //ProductModel productByCode = productModelService.getProductByCode(code);

        DigitalDownloadTrackerModel link = modelService.create(DigitalDownloadTrackerModel.class);
        //link.setDownloadToken(defaultCustomTokenService.generateToken(orderNum,code,email));
        link.setValidUntil(DateUtils.addDays(new Date(), 7));

        link.setProductCode(code);
        link.setOrderNum(orderNum);
        //link.setProduct(productByCode);
        link.setUserEmail(email);

        link.setMaxDownloads(maxDownload);
        link.setDownloadCount(0);

        modelService.save(link);
        modelService.refresh(link);
        //return link.getDownloadToken();
    }



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
            return "Download failed - Invalid user credentials.";
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
            return "Download Failed for:" + code + " from order: " + orderNum;
        }
        link.setDownloadCount(link.getDownloadCount() + 1);
        modelService.save(link);
        modelService.refresh(link);
        return "Download Success for: " + code + " from order: " + orderNum + ". Maximum download count pending for this asset: " + (link.getMaxDownloads()-link.getDownloadCount());
    }

    public ResponseDTO getSecuredDownloadAccessV1(String orderNum, String code, String email) throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            DigitalDownloadTrackerModel link = findDownloadLinkByOrderNumAndCode(orderNum, code);


            if (!email.equalsIgnoreCase(link.getUserEmail())) {
                //return "Download failed - Invalid user credentials.";
                responseDTO.setStatus("Error");
                responseDTO.setMessage("Download failed - Invalid user credentials");
                return responseDTO;            }
            if (link == null || new Date().after(link.getValidUntil())) {
                //return "Download failed - Link expired";
                responseDTO.setStatus("Error");
                responseDTO.setMessage("Download failed - Link expired");
                return responseDTO;             }
            if (link.getDownloadCount() >= link.getMaxDownloads()) {
                //return "Download failed - Download limit exceeded";
                responseDTO.setStatus("Error");
                responseDTO.setMessage("Download failed - Download limit exceeded");
                return responseDTO;             }

            //ProductModel productModel = link.getProduct();
            ProductModel productModel = productModelService.getProductByCode(code);
            String downloadUrl = productModel.getDownloadUrl();

            String presignedUrl = s3PresignedUrlService.generatePresignedUrl(bucketName, downloadUrl, Duration.ofMinutes(signedUrlValidityPeriod));

            boolean downloadRequestStatus = defaultDigitalProductService.downloadRequest(presignedUrl);

            if (!downloadRequestStatus) {
                //return "Download Failed for:" + code + " from order: " + orderNum;
                responseDTO.setStatus("Error");
                responseDTO.setMessage("Download Failed for:" + code + " from order: " + orderNum);
                return responseDTO;
            }
            link.setDownloadCount(link.getDownloadCount() + 1);
            modelService.save(link);
            modelService.refresh(link);


            responseDTO.setStatus("Success");
            //responseDTO.setMessage("Download Success for: " + code + " from order: " + orderNum + ". Maximum download count pending for this asset: " + (link.getMaxDownloads() - link.getDownloadCount()));
            DownloadDataResult dataResult = new DownloadDataResult();
            downloaddatapopulator.populate(link, dataResult);
            //responseDTO.setData(dataResult);
            responseDTO.setDownloadDataResult(dataResult);
            return responseDTO;
            //return "Download Success for: " + code + " from order: " + orderNum + ". Maximum download count pending for this asset: " + (link.getMaxDownloads() - link.getDownloadCount());
        } catch (CustomException ce) {
            responseDTO.setStatus("Error");
            responseDTO.setMessage("Error retrieving download link: " + ce.getCustomMessage());
            return responseDTO;
        }

    }

    private DownloadUrlPropsModel findDownloadLinkByToken(String token) {
        return productModelService.getDownloadUrlPropsModel(token);
    }

    private DigitalDownloadTrackerModel findDownloadLinkByOrderNumAndCode(String orderNum, String code) {
        return productModelService.getDigitalDownloadTrackerModel(orderNum, code);
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
