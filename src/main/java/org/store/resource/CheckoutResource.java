package org.store.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.store.domain.*;
import org.store.dto.request.CreateCheckoutRequest;
import org.store.service.*;


import java.time.LocalDate;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/checkout")
public class CheckoutResource {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private MailConstructor mailConstructor;
	
	@PostMapping()
	public  ResponseEntity<Order> checkoutPost(@RequestBody CreateCheckoutRequest checkoutRequest){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		ShoppingCart shoppingCart = userService.findByUsername(username).get().getShoppingCart();
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		User user = userService.findByUsername(username).get();
		Order order = orderService.createOrder(shoppingCart, checkoutRequest.getShippingAddress(), checkoutRequest.getBillingAddress(), checkoutRequest.getPayment(), checkoutRequest.getShippingMethod(), user);
		
		mailSender.send(mailConstructor.constructOrderConfirmationEmail(user, order, Locale.ENGLISH));
		
		shoppingCartService.clearShoppingCart(shoppingCart);
		
		LocalDate today = LocalDate.now();
		LocalDate estimatedDeliveryDate;
		if (checkoutRequest.getShippingMethod().equals("groundShipping")) {
			estimatedDeliveryDate=today.plusDays(5);
		} else {
			estimatedDeliveryDate=today.plusDays(3);
		}
		return  ResponseEntity.ok(order);
		
	}

}
