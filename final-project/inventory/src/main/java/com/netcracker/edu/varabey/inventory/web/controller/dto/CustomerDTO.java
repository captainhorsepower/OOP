package com.netcracker.edu.varabey.inventory.web.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDTO {
    private Long id;
    private String email;

    public CustomerDTO(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
