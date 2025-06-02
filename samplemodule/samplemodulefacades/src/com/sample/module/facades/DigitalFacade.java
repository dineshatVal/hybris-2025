package com.sample.module.facades;

import com.custom.occ.dto.CustomProductWsDTO;
import com.sample.module.facades.dto.ResponseDTO;

public interface DigitalFacade {
 String generateDownloadLink(String orderNum, String code, String email);
 //String getDownloadAccess(String downloadToken, String email) throws Exception;
 void generateDownloadLinkV1(String orderNum, String code, String email);
 String updateDigitalProduct(CustomProductWsDTO customProductWsDTO);

 String getSecuredDownloadAccess(String orderNum, String code, String email) throws Exception;
 ResponseDTO getSecuredDownloadAccessV1(String orderNum, String code, String email) throws Exception;


 }
