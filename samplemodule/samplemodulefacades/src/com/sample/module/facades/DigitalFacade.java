package com.sample.module.facades;

import com.custom.occ.dto.CustomProductWsDTO;
import com.sample.module.core.model.DownloadUrlPropsModel;
import de.hybris.platform.core.model.product.ProductModel;

public interface DigitalFacade {
 String generateDownloadLink(String code, String email);
 String getDownloadAccess(String downloadToken, String email) throws Exception;

 String updateDigitalProduct(CustomProductWsDTO customProductWsDTO);
}
