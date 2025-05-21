package com.sample.module.facades.customorder;

import com.sample.module.core.dto.DummyOrderRequestDTO;

import java.util.List;

public interface CustomOrderCreationFacade {
    String createCustomOrder(String userId, List<DummyOrderRequestDTO.ProductEntry> productEntryList);
}
