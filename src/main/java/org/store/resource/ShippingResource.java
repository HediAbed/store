package org.store.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.store.domain.User;
import org.store.domain.UserShipping;
import org.store.dto.UserShippingDto;
import org.store.dto.request.CreateUserShippingRequest;
import org.store.mapper.UserShippingMapper;
import org.store.service.UserService;
import org.store.service.UserShippingService;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shipping")
public class ShippingResource {
	@Autowired
	private UserService userService;

	@Autowired
	private UserShippingMapper userShippingMapper;
	
	@Autowired
	private UserShippingService userShippingService;
	
	@PostMapping()
	public ResponseEntity addNewUserShippingPost(@RequestBody CreateUserShippingRequest userShippingRequest) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByUsername(username).get();
		userService.updateUserShipping(userShippingMapper.toUserShipping(userShippingRequest), user);
		return new ResponseEntity("Shipping Added(Updated) Successfully!", HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<List<UserShippingDto>> getUserShippingList(){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByUsername(username).get();
		return ResponseEntity.ok(user.getUserShippingList().stream().map(userShippingMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new)));
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity removeUserShippingPost(@PathVariable Long id) {
		userShippingService.removeById(id);
		return new ResponseEntity("Shipping Removed Successfully!", HttpStatus.OK);
	}
	
	@GetMapping(value="/setDefault/{id}")
	public ResponseEntity setDefaultUserShippingPost(@PathVariable Long id){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByUsername(username).get();
		userService.setUserDefaultShipping(id, user);
		return new ResponseEntity("Set Default Shipping Successfully!", HttpStatus.OK);
	}
}
