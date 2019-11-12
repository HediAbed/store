package org.store.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.store.domain.Order;
import org.store.domain.User;

import java.util.Locale;

public interface MailConstructor {
    SimpleMailMessage constructNewUserEmail(User user, String password);
    MimeMessagePreparator constructOrderConfirmationEmail (User user, Order order, Locale locale);
}
