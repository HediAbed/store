package org.store.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.store.domain.User;
import org.store.domain.UserBilling;
import org.store.domain.UserPayment;
import org.store.service.UserPaymentService;
import org.store.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentResource {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserPaymentService userPaymentService;

	@PostMapping()
	public ResponseEntity addNewCreditCardPost (@RequestBody UserPayment userPayment) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByUsername(username).get();
		UserBilling userBilling = userPayment.getUserBilling();
		
		userService.updateUserBilling(userBilling, userPayment, user);
		
		return new ResponseEntity("Payment Added(Updated) Successfully!", HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity removePaymentPost(@PathVariable  Long id){
		userPaymentService.removeById(id);
		return new ResponseEntity("Payment Removed Successfully!", HttpStatus.OK);
	}
	
	@GetMapping(value="/setDefault/{id}")
	public ResponseEntity setDefaultPaymentPost(@PathVariable  Long id){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByUsername(username).get();

		userService.setUserDefaultPayment(id, user);
		
		return new ResponseEntity("Payment Removed Successfully!", HttpStatus.OK);
	}

	@GetMapping()
	public  ResponseEntity<List<UserPayment>> getUserPaymentList(){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByUsername(username).get();
		
		List<UserPayment> userPaymentList = user.getUserPaymentList();
		
		return  ResponseEntity.ok(userPaymentList);
	}
	
}
