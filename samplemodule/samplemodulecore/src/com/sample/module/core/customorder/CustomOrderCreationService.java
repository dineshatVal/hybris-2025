package com.sample.module.core.customorder;

import com.sample.module.core.dto.DummyOrderRequestDTO;
import de.hybris.platform.core.model.order.OrderModel;

import java.util.List;

public interface CustomOrderCreationService {
    OrderModel createCustomOrder(String userId, List<DummyOrderRequestDTO.ProductEntry> productEntryList);

}
