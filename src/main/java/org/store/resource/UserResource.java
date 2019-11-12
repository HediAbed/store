package org.store.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.store.config.security.SecurityUtility;
import org.store.domain.User;
import org.store.domain.enums.RoleType;
import org.store.dto.UserDto;
import org.store.dto.request.CreateUserRequest;
import org.store.dto.request.ForgetPasswordRequest;
import org.store.dto.request.UpdateUserRequest;
import org.store.mapper.UserMapper;
import org.store.service.MailConstructor;
import org.store.service.UserService;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserResource {

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private MailConstructor mailConstructor;

	@Autowired
	private JavaMailSender mailSender;

	@PostMapping(path = "/create")
	public ResponseEntity createUser(@RequestBody CreateUserRequest createUserRequest) {
		if (userService.findByUsername(createUserRequest.getUsername()).isPresent()) {
			return new ResponseEntity("usernameExists", HttpStatus.NOT_ACCEPTABLE);
		}

		if (userService.findByEmail(createUserRequest.getEmail()).isPresent()) {
			return new ResponseEntity("emailExists", HttpStatus.NOT_ACCEPTABLE);
		}
		User user = userMapper.toUser(createUserRequest);

		// Generate random password
		String password = SecurityUtility.randomPassword();
		user.setPassword(password);
		user.setEnabled(true);
		userService.createUser(user);

		//send password client email
		SimpleMailMessage email = mailConstructor.constructNewUserEmail(user, password);
		mailSender.send(email);

		return new ResponseEntity("User Added Successfully!",HttpStatus.CREATED);
	}

	@PostMapping(path = "/forgetPassword")
	public ResponseEntity forgetPassword(@RequestBody ForgetPasswordRequest fpRequest) {
		Optional<User> userOptional = userService.findByEmail(fpRequest.getEmail());

		if (!userOptional.isPresent()) {
			return new ResponseEntity("Email not found", HttpStatus.BAD_REQUEST);
		}
		User user = userOptional.get();
		String password = SecurityUtility.randomPassword();

		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);
		userService.saveUser(user);

		SimpleMailMessage newEmail = mailConstructor.constructNewUserEmail(user, password);
		mailSender.send(newEmail);

		return new ResponseEntity("Email sent!", HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity updateProfileInfo(@RequestBody UpdateUserRequest updateUserRequest) throws Exception{
		Optional<User> currentUserOptional = userService.findById(updateUserRequest.getId());
		
		if(!currentUserOptional.isPresent()) {
			return new ResponseEntity("User not found!", HttpStatus.BAD_REQUEST);
		}
		
		if(userService.findByEmail(updateUserRequest.getEmail()).isPresent()) {
			if(userService.findByEmail(updateUserRequest.getEmail()).get().getId() != currentUserOptional.get().getId()) {
				return new ResponseEntity("Email already taken!", HttpStatus.BAD_REQUEST);
			}
		}
		
		if(userService.findByUsername(updateUserRequest.getUsername()).isPresent()) {
			if(userService.findByUsername(updateUserRequest.getUsername()).get().getId() != currentUserOptional.get().getId()) {
				return new ResponseEntity("Username already taken!", HttpStatus.BAD_REQUEST);
			}
		}
		User currentUser =currentUserOptional.get();

			BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
			String dbPassword = currentUser.getPassword();
			
			if(null != updateUserRequest.getCurrentPassword())
			if(passwordEncoder.matches(updateUserRequest.getCurrentPassword(), dbPassword)) {
				if(updateUserRequest.getNewPassword() != null && !updateUserRequest.getNewPassword().isEmpty() && !updateUserRequest.getNewPassword().equals("")) {
					currentUser.setPassword(updateUserRequest.getNewPassword());
				}
			} else {
				return new ResponseEntity("Incorrect current password!", HttpStatus.BAD_REQUEST);
			}
		currentUser.setFirstName(updateUserRequest.getFirstName());
		currentUser.setLastName(updateUserRequest.getLastName());
		currentUser.setEmail(updateUserRequest.getEmail());
		currentUser.setUsername(updateUserRequest.getUsername());
		userService.saveUser(currentUser);
		return new ResponseEntity("Update Success", HttpStatus.OK);
	}

	@GetMapping()
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		return ResponseEntity.ok( userService.getAllUsers().stream()
				.filter(u -> u.getUserRoles().stream().anyMatch(userRole -> userRole.getRole().getName().equals(RoleType.ROLE_CLIENT)))
				.map(userMapper::toDto)
				.collect(Collectors.toList()));
	}

	@GetMapping(path = "/getCurrentUser")
	public User getCurrentUser() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = new User();
		if (null != username) {
			user = userService.findByUsername(username).get();
		}
		return user;
	}

	@GetMapping("/checkSession")
	public ResponseEntity checkSession() {
		return new ResponseEntity("Session Active!", HttpStatus.OK);
	}

	@GetMapping(value="/users/logout")
	public ResponseEntity logout(){
		SecurityContextHolder.clearContext();
		return new ResponseEntity("Logout Successfully!", HttpStatus.OK);
	}

}
