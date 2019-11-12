package org.store.service;


import org.store.domain.*;

public interface OrderService {
	
	Order createOrder(
            ShoppingCart shoppingCart,
            ShippingAddress shippingAddress,
            BillingAddress billingAddress,
            Payment payment,
            String shippingMethod,
            User user
    );

}
