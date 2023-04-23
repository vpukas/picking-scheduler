package com.ocado.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class Picker {
    String pickerId;
    LocalTime currentTime;
    BigDecimal totalValue = BigDecimal.ZERO;

    public Picker(String pickerId, LocalTime currentTime) {
        this.pickerId = pickerId;
        this.currentTime = currentTime;
    }
}
