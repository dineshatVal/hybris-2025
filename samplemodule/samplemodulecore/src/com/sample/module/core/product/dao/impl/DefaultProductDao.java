package com.sample.module.core.product.dao.impl;

import com.sample.module.core.dto.CustomException;
import com.sample.module.core.model.DigitalDownloadTrackerModel;
import com.sample.module.core.model.DownloadUrlPropsModel;
import com.sample.module.core.product.dao.ProductDao;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class DefaultProductDao implements ProductDao {

    @Autowired
    private FlexibleSearchService flexibleSearchService;

    @Autowired
    private CatalogVersionService catalogVersionService;

    @Override
    public ProductModel findProductByCode(String code) {
        String query = "SELECT {pk} FROM {Product} WHERE {code} = ?code";
        FlexibleSearchQuery fsQuery = new FlexibleSearchQuery(query);
        fsQuery.addQueryParameter("code", code);
        return flexibleSearchService.searchUnique(fsQuery);
    }

    @Override
    public List<ProductModel> findProductsForCatalog(String catalogId, String catalogVersion) {
        CatalogVersionModel catalogVer = catalogVersionService.getCatalogVersion(catalogId, catalogVersion);
        String query = "SELECT {pk} FROM {Product} WHERE {catalogVersion} = ?catalogVersion";
        FlexibleSearchQuery fsq = new FlexibleSearchQuery(query);
        fsq.addQueryParameter("catalogVersion", catalogVer);
        fsq.setCount(30);
        return flexibleSearchService.<ProductModel>search(fsq).getResult();
    }

    @Override
    public DownloadUrlPropsModel getDownloadUrlPropsModel(String token) {
        try {
            String query = "SELECT {pk} FROM {DownloadUrlProps} WHERE {downloadToken} = ?token";
            FlexibleSearchQuery fsq = new FlexibleSearchQuery(query);
            fsq.addQueryParameter("token", token);
            DownloadUrlPropsModel downloadUrlPropsModel = flexibleSearchService.searchUnique(fsq);
            return downloadUrlPropsModel;
        } catch(ModelNotFoundException me) {
            throw new CustomException("Validate emailId / ordernumber / product code provided" + me.getMessage());

        } catch(Exception e) {
            throw new CustomException("Validate emailId / ordernumber / product code provided" + e.getMessage());
        }

    }

    @Override
    public DigitalDownloadTrackerModel getDigitalDownloadTrackerModel(String orderNum, String code) {
        try {
            String query = "SELECT {pk} FROM {DigitalDownloadTracker} WHERE {orderNum} = ?orderNum";
            Map<String, Object> params = new HashMap<>();
            params.put("orderNum", orderNum);

            List<DigitalDownloadTrackerModel> results = flexibleSearchService.<DigitalDownloadTrackerModel>search(
                    new FlexibleSearchQuery(query, params)).getResult();
            if(results.isEmpty() ){
                throw new CustomException("Validate ordernumber provided");
            }

            List<DigitalDownloadTrackerModel> digitalDownloadTrackerModels = results.stream()
                    .filter(record -> code.equals(record.getProductCode()))
                    .collect(Collectors.toList());
            if (!digitalDownloadTrackerModels.isEmpty()) {
                return digitalDownloadTrackerModels.get(0);
            } else {
                throw new CustomException("Validate productCode provided for orderNum: " + orderNum);
            }
        } catch (ModelNotFoundException me) {
            throw new CustomException("Validate ordernumber provided" + me.getMessage());
        }

    }
}
