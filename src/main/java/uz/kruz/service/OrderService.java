package uz.kruz.service;

import uz.kruz.domain.Order;
import uz.kruz.domain.vo.OrderStatus;
import uz.kruz.dto.OrderDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService extends Service<OrderDTO, Order, Integer> {

    List<Order> findByUserId(Integer userId);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByOrderDateAfter(LocalDateTime date);

    List<Order> findByTotalAmountGreaterThan(Double amount);
}
