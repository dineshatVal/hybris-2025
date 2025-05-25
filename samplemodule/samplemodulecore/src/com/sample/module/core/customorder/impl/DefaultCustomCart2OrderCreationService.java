package com.sample.module.core.customorder.impl;

import com.sample.module.core.customorder.CustomCart2OrderCreationService;
import com.sample.module.core.dto.DummyOrderRequestDTO;
import de.hybris.platform.commerceservices.enums.SalesApplication;
import de.hybris.platform.commerceservices.order.CommerceCartService;
import de.hybris.platform.commerceservices.order.CommerceCheckoutService;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.commerceservices.service.data.CommerceCheckoutParameter;
import de.hybris.platform.commerceservices.service.data.CommerceOrderResult;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.order.delivery.DeliveryModeModel;
import de.hybris.platform.core.model.order.payment.PaymentInfoModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

public class DefaultCustomCart2OrderCreationService implements CustomCart2OrderCreationService {

    @Resource
    private BaseSiteService baseSiteService;

    @Resource
    private UserService userService;

    @Resource
    private ProductService productService;

    @Resource
    private CommerceCartService commerceCartService;

    @Resource
    private CommerceCheckoutService commerceCheckoutService;

    @Resource
    private ModelService modelService;

    @Resource
    private CommonI18NService commonI18NService;

    @Resource
    private CartService cartService;

    @Autowired
    private FlexibleSearchService flexibleSearchService;

    @Override
    public OrderModel createCustomOrderFromCart(String userId, List<DummyOrderRequestDTO.ProductEntry> productEntryList) {
        // 1. Set site and user context
        var site = baseSiteService.getBaseSiteForUID("digitalSite");
        baseSiteService.setCurrentBaseSite(site, false);

        var user = userService.getUserForUID(userId);
        userService.setCurrentUser(user);

        var currency = commonI18NService.getCurrency("USD");
        commonI18NService.setCurrentCurrency(currency);

        // 2. Create/Get cart
        //CartModel cart = commerceCartService.getSessionCart();
        CommerceCheckoutParameter checkoutParam = new CommerceCheckoutParameter();
        CartModel cart = cartService.getSessionCart();

        for(DummyOrderRequestDTO.ProductEntry productEntry : productEntryList) {
            ProductModel product = productService.getProductForCode(productEntry.getProductCode());
            if (product != null) {
                CommerceCartParameter addToCartParam = new CommerceCartParameter();
                addToCartParam.setEnableHooks(true);
                addToCartParam.setProduct(product);
                addToCartParam.setQuantity(productEntry.getQuantity());
                addToCartParam.setCart(cart);
                addToCartParam.setUser(user);

                //cart.setUser(user);

                try {
                    commerceCartService.addToCart(addToCartParam);
                    checkoutParam.setCart(cart);


                    //commerceCheckoutService.
                } catch (Exception e) {
                    throw new RuntimeException("Failed to add product to cart: " + productEntry.getProductCode(), e);
                }
            }
        }
        checkoutParam.setEnableHooks(true);

        // 4. Set delivery address (mock address or existing user address)
        AddressModel address = modelService.create(AddressModel.class);


        address.setFirstname("John");
        address.setLastname("Doe");
        address.setTown("New York");
        address.setEmail("john@example.com");
        address.setOwner(user);
        address.setCountry(commonI18NService.getCountry("US"));
        modelService.save(address);
        checkoutParam.setAddress(address);

        commerceCheckoutService.setDeliveryAddress(checkoutParam);

        // 5. Set delivery mode (must exist in your system)
        /*DeliveryModeModel deliveryMode = modelService.create(DeliveryModeModel.class);
        deliveryMode.setCode("premium-gross"); */// or an existing code in your system

        String query = "SELECT {pk} FROM {DeliveryMode} WHERE {code} = ?code";
        FlexibleSearchQuery fsQuery = new FlexibleSearchQuery(query);
        fsQuery.addQueryParameter("code", "premium-gross");

        SearchResult<DeliveryModeModel> result = flexibleSearchService.search(fsQuery);
        DeliveryModeModel deliveryMode = result.getResult().get(0); // handle if empty

        //modelService.save(deliveryMode);
        //CommerceCheckoutParameter checkoutParamDeliveryMode = new CommerceCheckoutParameter();
        //checkoutParamDeliveryMode.setDeliveryMode(deliveryMode);
        checkoutParam.setDeliveryMode(deliveryMode);
        //commerceCheckoutService.setDeliveryMode(checkoutParamDeliveryMode);
        commerceCheckoutService.setDeliveryMode(checkoutParam);

        // 6. Set payment info (can be null if using COD or mock)
        PaymentInfoModel paymentInfo = modelService.create(PaymentInfoModel.class);
        paymentInfo.setCode("mock-payment-test1");
        paymentInfo.setUser(user);
        modelService.save(paymentInfo);
        //CommerceCheckoutParameter checkoutParamPaymentMode = new CommerceCheckoutParameter();
        //checkoutParamDeliveryMode.setPaymentInfo(checkoutParamPaymentMode.getPaymentInfo());
        checkoutParam.setPaymentInfo(paymentInfo);
        commerceCheckoutService.setPaymentInfo(checkoutParam);

        // 7. Place order
        //CommerceCheckoutParameter checkoutParamFinal = new CommerceCheckoutParameter();
        //checkoutParamFinal.setCart(cart);
        //checkoutParamFinal.setEnableHooks(true);
        //checkoutParamFinal.setSalesApplication(SalesApplication.WEB);
        //checkoutParam.setCart(cart);
        //checkoutParam.setEnableHooks(true);
        checkoutParam.setSalesApplication(SalesApplication.WEB);

        try{
            CommerceOrderResult commerceOrderResult = commerceCheckoutService.placeOrder(checkoutParam);
            OrderModel order = commerceOrderResult.getOrder();
            System.out.println("✅ Order placed: " + order.getCode());
            return order;
        } catch (Exception e) {
            throw new RuntimeException("Failed to place order", e);
        }



    }
}
