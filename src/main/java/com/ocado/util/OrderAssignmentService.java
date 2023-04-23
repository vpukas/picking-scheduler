package com.ocado.util;

import com.ocado.entity.Order;
import com.ocado.entity.Picker;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrderAssignmentService {

    public static List<String> distribute(List<Order> orders, List<Picker> pickers, LocalTime endTime) {
        List<String> pickedOrders = new ArrayList<>();

        // Calculate the weight of each order
        for (Order order : orders) {
            BigDecimal weight = order.getOrderValue().divide(BigDecimal.valueOf(order.getPickingTime().toMinutes()), 2, BigDecimal.ROUND_HALF_UP);
            order.setWeight(weight);
        }

        // Sort orders by weight in descending order
        orders.sort(Comparator.comparing(Order::getWeight).reversed());

        // Assign orders to available pickers based on weight
        for (Order order : orders) {
            Picker currentPicker = pickers.stream()
                    .filter(picker -> picker
                            .getCurrentTime().plus(order.getPickingTime()).isBefore(order.getCompleteBy().plusMinutes(1)) && picker.getCurrentTime().plus(order.getPickingTime()).isBefore(endTime.plusMinutes(1)))
                    .min(Comparator.comparing(picker -> OrderAssignmentService.getWeightedValue(order)))
                    .orElse(null);
            if (currentPicker != null) {
                StringBuilder sb = new StringBuilder();
                pickedOrders.add(sb
                        .append(currentPicker.getPickerId()).append(" ")
                        .append(order.getOrderId()).append(" ")
                        .append(currentPicker.getCurrentTime())
                        .toString());
                currentPicker.setCurrentTime(currentPicker.getCurrentTime().plus(order.getPickingTime()));
                currentPicker.setTotalValue(currentPicker.getTotalValue().add(order.getOrderValue()));
            }
        }

        return pickedOrders;
    }

    private static BigDecimal getWeightedValue(Order order) {
        return order.getOrderValue().divide(BigDecimal.valueOf(order.getPickingTime().toMinutes()), 2, BigDecimal.ROUND_HALF_UP);
    }

}
