package com.netcracker.edu.varabey.service.validation;

import com.netcracker.edu.varabey.entity.Customer;

public interface CustomerValidator extends ServiceValidator<Customer, Long> {
    void checkName(String name);
    void checkAge(Integer age);
    void checkEmail(String email);
    void checkForPersist(Customer customer);
    Customer checkFoundByEmail(Customer customer, String email);
    void checkNotFoundByEmail(Customer customer, String email);
}
