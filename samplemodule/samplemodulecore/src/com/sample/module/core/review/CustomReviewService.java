package com.sample.module.core.review;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.customerreview.enums.CustomerReviewApprovalType;
import de.hybris.platform.customerreview.impl.DefaultCustomerReviewService;
import de.hybris.platform.customerreview.model.CustomerReviewModel;

public class CustomReviewService extends DefaultCustomerReviewService {

    private static final double AUTO_APPROVAL_THRESHOLD = 4.0; // Example: Approve if rating is 4 or above

    @Override
    public CustomerReviewModel createCustomerReview(Double rating, String headline, String comment,
                                                    UserModel user, ProductModel product) {
        System.out.println("Inside Custom Review Service");
        CustomerReviewModel review = super.createCustomerReview(rating, headline, comment, user, product);
        System.out.println("Inside Custom Review Service - 1");

        if (rating >= AUTO_APPROVAL_THRESHOLD) {
            review.setApprovalStatus(CustomerReviewApprovalType.APPROVED);
            System.out.println("Inside Custom Review Service - 2");

        } else {
            review.setApprovalStatus(CustomerReviewApprovalType.PENDING);
            System.out.println("Inside Custom Review Service - 3");

        }
        System.out.println("Inside Custom Review Service - 4");

        getModelService().save(review);
        System.out.println("Inside Custom Review Service - 5");

        return review;
    }
}
