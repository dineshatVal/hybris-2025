package com.sample.module.facades.populators;

import com.sample.module.facades.dto.CustomOrderResult;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.OrderModel;

import java.util.stream.Collectors;

public class OrderDataToDTOPopulator implements Populator<OrderModel, CustomOrderResult> {
    @Override
    public void populate(OrderModel source, CustomOrderResult target) {
        target.setOrderNum(source.getCode());
        String codesConcatenated = source.getEntries().stream()
                .map(f -> f.getProduct().getCode())
                .collect(Collectors.joining(","));

        target.setProductCodes(codesConcatenated);
    }
}
