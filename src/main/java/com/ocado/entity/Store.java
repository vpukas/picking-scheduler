package com.ocado.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Store {
    private List<String> pickers;
    private LocalTime pickingStartTime;
    private LocalTime pickingEndTime;
}
