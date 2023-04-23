package com.ocado;

import com.ocado.entity.Order;
import com.ocado.entity.Picker;
import com.ocado.entity.Store;
import com.ocado.util.DataReader;
import com.ocado.util.OrderAssignmentService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        DataReader dataReader = new DataReader();
        List<Order> orders = dataReader.readOrders(args[1]);
        Store store = dataReader.readStore(args[0]);
        List<Picker> pickers = new ArrayList<>();
        for (String s: store.getPickers()
             ) {
                pickers.add(new Picker(s, store.getPickingStartTime()));
        }
        OrderAssignmentService.distribute(orders, pickers, store.getPickingEndTime()).forEach(System.out::println);
    }
}
