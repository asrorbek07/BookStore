package uz.kruz.service.impl;

import uz.kruz.dto.BaseDTO;
import uz.kruz.domain.vo.OrderStatus;
import uz.kruz.repository.OrderRepository;
import uz.kruz.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class OrderServiceImpl<D extends BaseDTO> implements OrderService<D> {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public D register(D dto) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public Optional<D> findById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<D> findAll() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public boolean removeById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public D modify(D dto) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<D> findByUserId(Integer userId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<D> findByStatus(OrderStatus status) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<D> findByOrderDateAfter(LocalDateTime date) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<D> findByTotalAmountGreaterThan(Double amount) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}
