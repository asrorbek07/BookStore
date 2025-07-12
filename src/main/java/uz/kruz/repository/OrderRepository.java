package uz.kruz.repository;

import uz.kruz.domain.Order;
import uz.kruz.domain.vo.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends Repository<Order, Integer> {

    List<Order> retrieveByUserId(Integer userId);

    List<Order> retrieveByStatus(OrderStatus status);

    List<Order> retrieveByOrderDateAfter(LocalDateTime date);

    List<Order> retrieveByTotalAmountGreaterThan(Double amount);
}
