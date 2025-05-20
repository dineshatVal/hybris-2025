package com.sample.module.facades;

import com.custom.occ.dto.CustomProductWsDTO;

public interface DigitalFacade {
 String generateDownloadLink(String orderNum, String code, String email);
 //String getDownloadAccess(String downloadToken, String email) throws Exception;

 String updateDigitalProduct(CustomProductWsDTO customProductWsDTO);

 String getSecuredDownloadAccess(String orderNum, String code, String email) throws Exception;

}
