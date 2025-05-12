package com.sample.module.core.product.dao.impl;

import com.sample.module.core.model.DownloadUrlPropsModel;
import com.sample.module.core.product.dao.ProductDao;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        String query = "SELECT {pk} FROM {DownloadUrlProps} WHERE {downloadToken} = ?token";
        FlexibleSearchQuery fsq = new FlexibleSearchQuery(query);
        fsq.addQueryParameter("token", token);
        DownloadUrlPropsModel downloadUrlPropsModel = flexibleSearchService.searchUnique(fsq);
        return downloadUrlPropsModel;
    }
}