package uz.kruz.service;

import uz.kruz.domain.OrderItem;
import uz.kruz.dto.BaseDTO;
import uz.kruz.dto.OrderItemDTO;

import java.util.List;

public interface OrderItemService extends Service<OrderItemDTO, OrderItem, Integer> {

    List<OrderItem> findByOrderId(Integer orderId);

    List<OrderItem> findByBookId(Integer bookId);

    List<OrderItem> findByQuantityGreaterThan(Integer quantity);
}
