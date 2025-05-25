package com.sample.module.core.customorder.impl;

import com.sample.module.core.customorder.CustomOrderCreationService;
import com.sample.module.core.dto.DummyOrderRequestDTO;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.OrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.jalo.order.price.PriceInformation;
import de.hybris.platform.product.PriceService;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class DefaultCustomOrderCreationService implements CustomOrderCreationService {

    @Resource
    private ModelService modelService;

    @Resource
    private UserService userService;

    @Resource
    private ProductService productService;

    @Resource
    private CommonI18NService commonI18NService;

    @Resource
    private BaseSiteService baseSiteService;

    @Resource
    private PriceService priceService;

    @Override
    public OrderModel createCustomOrder(String userId, List<DummyOrderRequestDTO.ProductEntry> productEntryList) {
       // try {
            // Load user and product
            UserModel user = userService.getUserForUID(userId);
            // Create order
            OrderModel order = modelService.create(OrderModel.class);
            order.setUser(user);
            order.setDate(new Date());
            order.setCode("DUMMY-" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6).toUpperCase());
            order.setCurrency(commonI18NService.getCurrency("USD"));
            modelService.save(order);

            List<AbstractOrderEntryModel> orderEntryModelList = new java.util.ArrayList<>();
            //List<DummyOrderRequestDTO.ProductEntry> productEntries = dummyOrderRequestDTO.getProductEntries();

            for (DummyOrderRequestDTO.ProductEntry productEntry : productEntryList) {
                ProductModel product = productService.getProductForCode(productEntry.getProductCode());

                OrderEntryModel entry = modelService.create(OrderEntryModel.class);
                entry.setProduct(product);
                entry.setQuantity(productEntry.getQuantity());

                List<PriceInformation> prices = priceService.getPriceInformationsForProduct(product);
                if (prices != null && !prices.isEmpty()) {
                    entry.setTotalPrice(prices.get(0).getPriceValue().getValue());
                }

                entry.setOrder(order);
                if(Objects.nonNull(product.getUnit())){
                    entry.setUnit(product.getUnit());
                } else {
                    UnitModel model = new UnitModel();
                    //model.
                    entry.setUnit(model);
                }

                modelService.save(entry);
                orderEntryModelList.add(entry);
            }
            // TODO: set order site
            order.setSite(baseSiteService.getCurrentBaseSite());
            // Add entry to order
            order.setEntries(orderEntryModelList);
            //modelService.save(entry);
            modelService.save(order);
            return order;

    }
}
