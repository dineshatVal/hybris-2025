package com.sample.module.facades;

import com.custom.occ.dto.CustomProductWsDTO;

public interface DigitalFacade {
 String generateDownloadLink(String code, String email);
 String getDownloadAccess(String downloadToken, String email) throws Exception;

 String updateDigitalProduct(CustomProductWsDTO customProductWsDTO);

 String getSecuredDownloadAccess(String downloadToken, String email) throws Exception;

}
