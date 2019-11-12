package org.store.service;

import org.store.domain.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    PasswordResetToken findToken(final String token);
    void createPasswordResetTokenForUser(final User user, final String token);
    User createUser(User user, Set<UserRole> userRoles);
    User createUser(User user);
    User saveUser(User user);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail (String email);
    List<User> getUsersByRole(Role role);
    List<User> getAllUsers();
    Optional<User> findById(Long id);

    void updateUserPaymentInfo(UserBilling userBilling, UserPayment userPayment, User user);

    void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user);

    void setUserDefaultPayment(Long userPaymentId, User user);

    void updateUserShipping(UserShipping userShipping, User user);

    void setUserDefaultShipping(Long userShippingId, User user);
}

