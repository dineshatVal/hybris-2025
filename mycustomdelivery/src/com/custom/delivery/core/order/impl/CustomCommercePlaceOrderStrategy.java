package com.custom.delivery.core.order.impl;

import de.hybris.platform.commerceservices.order.impl.DefaultCommercePlaceOrderStrategy;
import de.hybris.platform.commerceservices.service.data.CommerceCheckoutParameter;
import de.hybris.platform.commerceservices.service.data.CommerceOrderResult;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.order.InvalidCartException;

public class CustomCommercePlaceOrderStrategy extends DefaultCommercePlaceOrderStrategy {

    @Override
    public CommerceOrderResult placeOrder(CommerceCheckoutParameter parameter) throws IllegalArgumentException, InvalidCartException {
        CommerceOrderResult result = super.placeOrder(parameter);
        OrderModel order = result.getOrder();
        CartModel cart = parameter.getCart();

        if (cart.getDeliveryMethod() != null) {
            order.setDeliveryMethod(cart.getDeliveryMethod());
            getModelService().save(order);
        }

        return result;
    }
}
