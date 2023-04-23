package com.ocado.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.ocado.entity.Order;
import com.ocado.entity.Store;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.List;

@NoArgsConstructor
public class DataReader {
    ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    public List<Order> readOrders(String filePath) throws IOException {
        return mapper.readValue(new File(filePath),
                new TypeReference<>() {
                });
    }
    public Store readStore (String filePath) throws IOException {
        return mapper.readValue(new File(filePath),
                Store.class);
    }
}
