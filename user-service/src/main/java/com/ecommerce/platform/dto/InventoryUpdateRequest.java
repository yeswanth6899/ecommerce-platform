package com.ecommerce.platform.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@JsonIgnoreProperties(ignoreUnknown = false)
public class InventoryUpdateRequest {

    @NotNull(message = "Available Stock is required")
    @PositiveOrZero(message = "Available Stock cannot be negative")
    private Integer availableStock;

    @NotNull(message = "Reorder level is required")
    @PositiveOrZero(message = "Reorder level cannot be negative")
    private Integer reorderLevel;

    public InventoryUpdateRequest() {
    }

    public InventoryUpdateRequest(Integer availableStock,
                                  Integer reorderLevel) {
        this.availableStock = availableStock;
        this.reorderLevel = reorderLevel;
    }

    public Integer getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Integer availableStock) {
        this.availableStock = availableStock;
    }

    public Integer getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
    }
}