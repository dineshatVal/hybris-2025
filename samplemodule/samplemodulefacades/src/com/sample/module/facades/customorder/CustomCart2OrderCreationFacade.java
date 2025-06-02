package com.sample.module.facades.customorder;

import com.sample.module.core.dto.DummyOrderRequestDTO;
import com.sample.module.facades.dto.ResponseDTO;
import de.hybris.platform.core.model.order.OrderModel;

import java.util.List;

public interface CustomCart2OrderCreationFacade {
    ResponseDTO createCustomOrderFromCart(String userId, List<DummyOrderRequestDTO.ProductEntry> productEntryList);
}
