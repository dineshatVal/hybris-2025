package com.sample.module.core.customorder;

import com.sample.module.core.dto.DummyOrderRequestDTO;
import de.hybris.platform.core.model.order.OrderModel;

import java.util.List;

public interface CustomCart2OrderCreationService {
    OrderModel createCustomOrderFromCart(String userId, List<DummyOrderRequestDTO.ProductEntry> productEntryList);

}
