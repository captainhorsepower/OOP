package com.netcracker.edu.varabey.inventory.web.validation;

import com.netcracker.edu.varabey.inventory.data.entity.Customer;
import com.netcracker.edu.varabey.inventory.data.entity.Order;
import com.netcracker.edu.varabey.inventory.data.entity.OrderItem;
import com.netcracker.edu.varabey.inventory.springutils.beanannotation.Logged;
import com.netcracker.edu.varabey.inventory.springutils.beanannotation.Validator;
import com.netcracker.edu.varabey.inventory.web.validation.exceptions.OrderException;
import com.netcracker.edu.varabey.inventory.web.validation.fragments.DateTimeValidator;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Validator
public class DefaultOrderValidator implements OrderValidator {
    private final DateTimeValidator dateTimeValidator;
    private final CustomerValidator customerValidator;
    private final OrderItemValidator orderItemValidator;

    public DefaultOrderValidator(DateTimeValidator dateTimeValidator, CustomerValidator customerValidator, OrderItemValidator orderItemValidator) {
        this.dateTimeValidator = dateTimeValidator;
        this.customerValidator = customerValidator;
        this.orderItemValidator = orderItemValidator;
    }

    @Override
    public void checkNotNull(Order resource) {
        if (resource == null) {
            throw new OrderException("Order must be NOT null, but is.");
        }
    }

    @Override
    public void checkIdIsNull(Long id) {
        if (id != null) {
            throw new OrderException("Order's id must be null");
        }
    }

    @Override
    public void checkIdIsNotNull(Long id) {
        if (id == null) {
            throw new OrderException("Order's id must be NOT null");
        }
    }

    @Override
    public void checkCustomer(Customer c) {
        customerValidator.checkAllProperties(c);
    }

    @Override
    public void checkCreationDate(LocalDateTime dateTime) {
        dateTimeValidator.check(dateTime);
    }

    @Override
    public void checkOrderItem(OrderItem orderItem) {
        orderItemValidator.checkAllProperties(orderItem);
    }

    @Logged(messageBefore = "Verifying order is found...")
    @Override
    public Order checkFound(Order order, String notFoundMessage) {
        if (order == null) {
            throw new OrderException(notFoundMessage, HttpStatus.NOT_FOUND);
        }
        return order;
    }

    @Logged(messageBefore = "Verifying order is found by id...")
    @Override
    public Order checkFoundById(Order order, Long id) {
        return checkFound(order, "Order with id=" + id + " was not found");
    }


    @Logged(messageBefore = "Verifying all order properties...")
    @Override
    public void checkAllProperties(Order order) {
        checkNotNull(order);
        checkCustomer(order.getCustomer());
        checkCreationDate(order.getCreatedOnDate());
        order.getItems().forEach(this::checkOrderItem);
    }

    @Logged(messageBefore = "Verifying order is eligible for updates...")
    @Override
    public void checkEligibilityForUpdate(Order order) {
        if (order.isPaid()) {
            throw new OrderException("Order is already paid. Paid Orders can not be modified.");
        }
    }

    @Logged(messageBefore = "Verifying order for persist...")
    @Override
    public void checkForPersist(Order order) {
        checkNotNull(order);
        checkIdIsNull(order.getId());
        checkAllProperties(order);
    }
}
