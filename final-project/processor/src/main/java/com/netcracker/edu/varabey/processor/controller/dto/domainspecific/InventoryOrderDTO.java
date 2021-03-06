package com.netcracker.edu.varabey.processor.controller.dto.domainspecific;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netcracker.edu.varabey.processor.controller.dto.OrderItemDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class InventoryOrderDTO {
    private Long id;
    private String email;
    private Boolean isPaid;
    private String orderStatus;
    private LocalDateTime createdOnDate;
    private Double totalPrice;
    private Integer itemCount;
    private List<OrderItemDTO> items = new ArrayList<>();

    @JsonIgnore
    public Boolean isPaid() {
        return this.isPaid;
    }

    public void setPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }
}
