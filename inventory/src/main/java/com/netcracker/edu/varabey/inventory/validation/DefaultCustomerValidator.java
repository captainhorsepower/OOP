package com.netcracker.edu.varabey.inventory.validation;

import com.netcracker.edu.varabey.inventory.data.entity.Customer;
import com.netcracker.edu.varabey.inventory.exceptions.CustomerException;
import com.netcracker.edu.varabey.inventory.util.aop.beanannotation.Logged;
import com.netcracker.edu.varabey.inventory.util.aop.beanannotation.Validator;
import com.netcracker.edu.varabey.inventory.validation.fragments.EmailValidator;

@Validator
public class DefaultCustomerValidator implements CustomerValidator {
    private final EmailValidator emailValidator;

    public DefaultCustomerValidator(EmailValidator emailValidator) {
        this.emailValidator = emailValidator;
    }

    @Override
    public void checkNotNull(Customer c) {
        if (c == null) {
            throw new CustomerException("Customer must not be null");
        }
    }

    @Override
    public void checkIdIsNull(Long id) {
        if (id != null) {
            throw new CustomerException("Customer's id must be null");
        }
    }

    @Override
    public void checkIdIsNotNull(Long id) {
        if (id == null) {
            throw new CustomerException("Customer's id must be NOT null");
        }
    }

    @Logged(messageBefore = "Verifying customer's email...")
    @Override
    public void checkEmail(String email) {
        emailValidator.check(email);
    }

    @Override
    public Customer checkFound(Customer customer, String notFoundMessage) {
        if (customer == null) {
            throw new CustomerException(notFoundMessage);
        }
        return customer;
    }

    @Logged(messageBefore = "Making sure customer is found by email...")
    @Override
    public Customer checkFoundByEmail(Customer customer, String email) {
        return checkFound(customer, "Customer with email=\'" + email + "\' was not found");
    }

    @Logged(messageBefore = "Making sure customer is found by id...")
    @Override
    public Customer checkFoundById(Customer customer, Long id) {
        return checkFound(customer, "Customer with id=" + id + " was not found");
    }

    @Override
    public void checkAllProperties(Customer customer) {
        checkEmail(customer.getEmail());
    }

    @Override
    public void checkForPersist(Customer customer) {
        checkNotNull(customer);
        checkIdIsNull(customer.getId());
        checkAllProperties(customer);
    }
}