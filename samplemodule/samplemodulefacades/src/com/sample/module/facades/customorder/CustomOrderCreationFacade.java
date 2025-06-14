package com.sample.module.facades.customorder;

import com.sample.module.core.dto.DummyOrderRequestDTO;
import de.hybris.platform.core.model.order.OrderModel;

import java.util.List;

public interface CustomOrderCreationFacade {
    OrderModel createCustomOrder(String userId, List<DummyOrderRequestDTO.ProductEntry> productEntryList);
}
