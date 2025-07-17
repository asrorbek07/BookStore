package uz.kruz.service.impl;

import uz.kruz.domain.Order;
import uz.kruz.domain.vo.OrderStatus;
import uz.kruz.dto.OrderDTO;
import uz.kruz.repository.OrderRepository;
import uz.kruz.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order register(OrderDTO dto) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<Order> findById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Order> findAll() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public boolean removeById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Order modify(OrderDTO dto) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Order> findByUserId(Integer userId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Order> findByOrderDateAfter(LocalDateTime date) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<Order> findByTotalAmountGreaterThan(Double amount) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}
