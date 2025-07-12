package uz.kruz.repository;

import uz.kruz.domain.OrderItem;

import java.util.List;

public interface OrderItemRepository extends Repository<OrderItem, Integer> {

    List<OrderItem> retrieveByOrderId(Integer orderId);

    List<OrderItem> retrieveByBookId(Integer bookId);

    List<OrderItem> retrieveByQuantityGreaterThan(Integer quantity);
}
