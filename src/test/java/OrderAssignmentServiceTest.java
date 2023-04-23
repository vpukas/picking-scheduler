import com.ocado.entity.Order;
import com.ocado.entity.Picker;
import com.ocado.util.OrderAssignmentService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderAssignmentServiceTest {

    @Test
    public void testDistributeOrders() {
        // Create some orders
        List<Order> orders = new ArrayList<>();
        Order order1 = Order.builder()
                .orderId("order1")
                .orderValue(BigDecimal.valueOf(10))
                .pickingTime(Duration.ofMinutes(5))
                .completeBy(LocalTime.of(10, 0))
                .build();
        Order order2 = Order.builder()
                .orderId("order2")
                .orderValue(BigDecimal.valueOf(20))
                .pickingTime(Duration.ofMinutes(10))
                .completeBy(LocalTime.of(10, 15))
                .build();
        Order order3 = Order.builder()
                .orderId("order3")
                .orderValue(BigDecimal.valueOf(30))
                .pickingTime(Duration.ofMinutes(15))
                .completeBy(LocalTime.of(10, 30))
                .build();
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        // Create some pickers
        List<Picker> pickers = new ArrayList<>();
        Picker picker1 = new Picker("picker1", LocalTime.of(9, 0));
        pickers.add(picker1);

        // Distribute the orders
        List<String> result = OrderAssignmentService.distribute(orders, pickers, LocalTime.of(11, 0));

        // Verify the result
        assertEquals(3, result.size());
        assertTrue(result.contains("picker1 order1 09:00"));
        assertTrue(result.contains("picker1 order2 09:05"));
        assertTrue(result.contains("picker1 order3 09:15"));
    }

    @Test
    void distribute_shouldNotAssignOrdersToPickersIfEndTimeIsReached() {
        // given
        Order order1 = Order.builder().orderId("1").orderValue(BigDecimal.valueOf(10)).pickingTime(Duration.ofMinutes(5)).completeBy(LocalTime.of(10, 0)).build();
        Order order2 = Order.builder().orderId("2").orderValue(BigDecimal.valueOf(15)).pickingTime(Duration.ofMinutes(10)).completeBy(LocalTime.of(11, 0)).build();
        Order order3 = Order.builder().orderId("3").orderValue(BigDecimal.valueOf(5)).pickingTime(Duration.ofMinutes(2)).completeBy(LocalTime.of(9, 0)).build();
        List<Order> orders = Arrays.asList(order1, order2, order3);

        Picker picker1 = new Picker("1", LocalTime.of(8, 0));
        List<Picker> pickers = List.of(picker1);

        // when
        List<String> pickedOrders = OrderAssignmentService.distribute(orders, pickers, LocalTime.of(8, 15));

        // then
        assertNotNull(pickedOrders);
        assertEquals(2, pickedOrders.size());
    }
}