package com.custom.delivery.core.calculation;

import com.custom.delivery.model.StoreDeliveryConfigModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.order.exceptions.CalculationException;
import de.hybris.platform.order.impl.DefaultCalculationService;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.store.BaseStoreModel;

import javax.annotation.Resource;

public class CustomCalculationService extends DefaultCalculationService {

    @Resource(name = "configurationService")
    private ConfigurationService configurationService;

    @Override
    public void calculateTotals(AbstractOrderModel order, boolean recalculate) throws CalculationException {
        super.calculateTotals(order, recalculate);

        if (order instanceof CartModel && ((CartModel) order).getDeliveryMethod() != null && isDeliveryChargeEnabled()) {
            CartModel cart = (CartModel) order;
            double deliveryCharge = calculateDeliveryCharge(cart);
            double baseTotal = cart.getSubtotal() != null ? cart.getSubtotal() : 0.0;
            double newTotal = baseTotal + deliveryCharge;

            cart.setTotalPrice(newTotal);
            cart.setDeliveryCost(deliveryCharge);

            CurrencyModel currency = cart.getCurrency();
            if (currency != null) {
                int digits = currency.getDigits() != null ? currency.getDigits() : 2;
                cart.setTotalPrice(Math.round(newTotal * Math.pow(10, digits)) / Math.pow(10, digits));
            }

            getModelService().save(cart);
        }
    }

    private boolean isDeliveryChargeEnabled() {
        return configurationService.getConfiguration().getBoolean("custom.delivery.charge.enabled", false);
    }

    private double calculateDeliveryCharge(CartModel cart) {
        BaseStoreModel store = cart.getStore();
        if (store != null) {
            StoreDeliveryConfigModel config = getStoreDeliveryConfig(store);
            if (config != null) {
                if ("Standard".equals(cart.getDeliveryMethod().getCode())) {
                    return config.getStandardDeliveryCharge();
                } else if ("Express".equals(cart.getDeliveryMethod().getCode())) {
                    return config.getExpressDeliveryCharge();
                }
            }
        }
        // Fallback to default charges from local.properties
        if ("Standard".equals(cart.getDeliveryMethod().getCode())) {
            return configurationService.getConfiguration().getDouble("delivery.charge.standard", 5.0);
        } else if ("Express".equals(cart.getDeliveryMethod().getCode())) {
            return configurationService.getConfiguration().getDouble("delivery.charge.express", 10.0);
        }
        return 0.0;
    }

    private StoreDeliveryConfigModel getStoreDeliveryConfig(BaseStoreModel store) {
        // Assume StoreDeliveryConfigService is implemented to fetch config by store
        return getModelService().create(StoreDeliveryConfigModel.class); // Placeholder; implement actual logic
    }
}
