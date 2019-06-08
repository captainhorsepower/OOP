package com.netcracker.edu.varabey.inventory.controller.dto.transformer;

import com.netcracker.edu.varabey.inventory.controller.dto.CustomerDTO;
import com.netcracker.edu.varabey.inventory.data.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerTransformer implements Transformer<Customer, CustomerDTO> {
    @Override
    public Customer toEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setEmail( (dto.getEmail() == null) ? "" : dto.getEmail().trim().toLowerCase() );
        return customer;
    }

    @Override
    public CustomerDTO toDto(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getEmail());
    }
}