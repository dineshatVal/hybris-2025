package com.sample.module.core.loyalty;

import com.sample.module.core.model.LoyaltyRuleModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;
import java.util.List;

public class LoyaltyPointStrategy {

    @Resource
    private ModelService modelService;

    @Resource(name = "loyaltyRuleService")
    private LoyaltyRuleService loyaltyRuleService;

    public void awardPoints(OrderModel order) {
            int totalPoints = 0;

            for (AbstractOrderEntryModel entry : order.getEntries()) {
                LoyaltyRuleModel rule = loyaltyRuleService.getRuleForCategory((List<CategoryModel>) entry.getProduct().getSupercategories());
                if (rule != null) {
                    double points = entry.getTotalPrice().doubleValue() * rule.getPointsPerCurrency();
                    totalPoints += (int) points;
                }
            }

            CustomerModel customer = (CustomerModel) order.getUser();
            if(customer.getLoyaltyPoints() == null){
                customer.setLoyaltyPoints(0);
                modelService.save(customer);
            }
            customer.setLoyaltyPoints(customer.getLoyaltyPoints() + totalPoints);
            modelService.save(customer);
    }
}
