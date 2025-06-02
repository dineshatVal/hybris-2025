package com.sample.module.facades.populators;

import com.sample.module.core.dto.ProductDTO;
import com.sample.module.core.jalo.DigitalDownloadTracker;
import com.sample.module.core.model.DigitalDownloadTrackerModel;
import com.sample.module.facades.dto.DownloadDataResult;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;

public class DigitalDownloadDataToDTOPopulator implements Populator<DigitalDownloadTrackerModel, DownloadDataResult> {
    @Override
    public void populate(DigitalDownloadTrackerModel source, DownloadDataResult target) {
        target.setOrderNum(source.getOrderNum());
        target.setRemainingDownloads(source.getMaxDownloads()- source.getDownloadCount());
        target.setProductCode(source.getProductCode());
    }
}
