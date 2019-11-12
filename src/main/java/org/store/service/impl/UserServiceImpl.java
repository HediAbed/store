package org.store.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.store.config.security.SecurityUtility;
import org.store.domain.*;
import org.store.domain.enums.RoleType;
import org.store.repository.*;
import org.store.service.RoleService;
import org.store.service.UserService;

import java.util.*;

@Service("userDetailsService")
public class UserServiceImpl implements UserDetailsService, UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UserBillingRepository userBillingRepository;

    @Autowired
    private UserPaymentRepository userPaymentRepository;

    @Autowired
    private UserShippingRepository userShippingRepository;

    @Override
    @Transactional
    public User createUser(User user) {
        User localUser = userRepository.findByUsername(user.getUsername());

        if(localUser != null) {
            LOGGER.info("User "+user.getUsername()+" already exist. Nothing will be done. ");
        } else {
            //Generate a ROLE_CLIENT IF NOT EXIST
            Optional<Role> roleOptional = roleService.findByName(RoleType.ROLE_CLIENT);
            Set<UserRole> userRoles = new HashSet<>();
            if (roleOptional.isPresent()){
                userRoles.add(new UserRole(user, roleOptional.get()));
            }else {
                Role role =new Role();
                role.setName(RoleType.ROLE_CLIENT);
                userRoles.add(new UserRole(user, role));
                for (UserRole ur : userRoles) {
                    roleService.save(ur.getRole());
                }
            }
            user.getUserRoles().addAll(userRoles);
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUser(user);
            user.setShoppingCart(shoppingCart);
            user.setUserPaymentList(new ArrayList<UserPayment>());
            user.setUserShippingList(new ArrayList<UserShipping>());

            String encryptedPassword = SecurityUtility.passwordEncoder().encode(user.getPassword());
            user.setPassword(encryptedPassword);
            localUser = userRepository.save(user);
        }
        return localUser;
    }
    @Override
    @Transactional
    public User createUser(User user, Set<UserRole> userRoles) {
        User localUser = userRepository.findByUsername(user.getUsername());

        if(localUser != null) {
            LOGGER.info("User "+user.getUsername()+" already exist. Nothing will be done. ");
        } else {
            for (UserRole ur : userRoles) {
                roleService.save(ur.getRole());
            }
            user.getUserRoles().addAll(userRoles);
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUser(user);
            user.setShoppingCart(shoppingCart);
            user.setUserPaymentList(new ArrayList<UserPayment>());
            user.setUserShippingList(new ArrayList<UserShipping>());

            String encryptedPassword = SecurityUtility.passwordEncoder().encode(user.getPassword());
            user.setPassword(encryptedPassword);
            localUser = userRepository.save(user);
        }
        return localUser;
    }
    @Override
    public User saveUser(User user) {
        String encryptedPassword = SecurityUtility.passwordEncoder().encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return userRepository.saveAndFlush(user);
    }
    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        List<User> users = new ArrayList<>();
        userRoleRepository.findByRole(role).forEach(ur -> users.add(ur.getUser()));
        return users;
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userRepository.findOne(id));
//        return userRepository.findById(id);
    }

    @Override
    public void updateUserPaymentInfo(UserBilling userBilling, UserPayment userPayment, User user) {
        userRepository.saveAndFlush(user);
        userBillingRepository.save(userBilling);
        userPaymentRepository.save(userPayment);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User client = userRepository.findByUsername(username);
        if (client == null) {
            LOGGER.info("User " + username + " was not found in the database");
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
        return client;

    }

    @Override
    public PasswordResetToken findToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(myToken);
    }


    @Override
    public void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user) {
        userPayment.setUser(user);
        userPayment.setUserBilling(userBilling);
        userPayment.setDefaultPayment(true);
        userBilling.setUserPayment(userPayment);
        user.getUserPaymentList().add(userPayment);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void setUserDefaultPayment(Long userPaymentId, User user) {
        List<UserPayment> userPaymentList = (List<UserPayment>) userPaymentRepository.findAll();

        for (UserPayment userPayment : userPaymentList) {
            if(userPayment.getId() == userPaymentId) {
                userPayment.setDefaultPayment(true);
                userPaymentRepository.save(userPayment);
            } else {
                userPayment.setDefaultPayment(false);
                userPaymentRepository.save(userPayment);
            }
        }
    }

    @Override
    public void updateUserShipping(UserShipping userShipping, User user) {
        userShipping.setUser(user);
        userShipping.setUserShippingDefault(true);
        user.getUserShippingList().add(userShipping);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void setUserDefaultShipping(Long userShippingId, User user) {
        List<UserShipping> userShippingList = (List<UserShipping>) userShippingRepository.findAll();

        for (UserShipping userShipping : userShippingList) {
            if(userShipping.getId() == userShippingId) {
                userShipping.setUserShippingDefault(true);
                userShippingRepository.save(userShipping);
            } else {
                userShipping.setUserShippingDefault(false);
                userShippingRepository.save(userShipping);
            }
        }
    }
}
