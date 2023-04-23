package com.ocado.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order {
    String orderId;
    BigDecimal orderValue;
    Duration pickingTime;
    LocalTime completeBy;
    BigDecimal weight = BigDecimal.valueOf(0L);
}
