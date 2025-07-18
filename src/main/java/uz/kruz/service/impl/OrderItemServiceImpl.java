package uz.kruz.service.impl;

import uz.kruz.domain.OrderItem;
import uz.kruz.dto.OrderItemDTO;
import uz.kruz.repository.OrderItemRepository;
import uz.kruz.service.OrderItemService;

import java.util.List;
import java.util.Optional;

public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem register(OrderItemDTO dto) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<OrderItem> findById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<OrderItem> findAll() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public boolean removeById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public OrderItem modify(OrderItemDTO dto, Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<OrderItem> findByOrderId(Integer orderId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<OrderItem> findByBookId(Integer bookId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<OrderItem> findByQuantityGreaterThan(Integer quantity) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}
