package com.sample.module.core.loyalty;

import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.order.events.SubmitOrderEvent;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import org.apache.log4j.Logger;

public class CustomLoyaltyPointsEventListener extends AbstractEventListener<SubmitOrderEvent> {

    private static final org.apache.log4j.Logger LOG = Logger.getLogger(CustomLoyaltyPointsEventListener.class);

    private LoyaltyPointStrategy loyaltyPointStrategy;

    public void setLoyaltyPointStrategy(LoyaltyPointStrategy loyaltyPointStrategy) {
        this.loyaltyPointStrategy = loyaltyPointStrategy;
    }

    @Override
    protected void onEvent(SubmitOrderEvent event) {
        LOG.info("SubmitOrderEvent received for order: " + event.getOrder().getCode());
        OrderModel order = event.getOrder();
        if (order != null) {
            loyaltyPointStrategy.awardPoints(order);
        }
    }
}
