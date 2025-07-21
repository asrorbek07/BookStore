package uz.kruz.service.impl;

import uz.kruz.domain.Order;
import uz.kruz.domain.vo.OrderStatus;
import uz.kruz.dto.OrderDTO;
import uz.kruz.repository.OrderRepository;
import uz.kruz.service.OrderService;
import uz.kruz.util.Check.OrderChecks;

import java.math.BigDecimal;
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
        OrderChecks.registerCheck(dto);
        Order order = Order.builder()
                .userId(dto.getUserId())
                .totalAmount(BigDecimal.valueOf(dto.getTotalAmount()))
                .status(dto.getStatus()).build();
        return orderRepository.create(order);

    }

    @Override
    public Optional<Order> findById(Integer id) {
        OrderChecks.findByIdCheck(id);
        return orderRepository.retrieveById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.retrieveAll();
    }

    @Override
    public boolean removeById(Integer id) {
        OrderChecks.removeByIdCheck(id);
        return orderRepository.deleteById(id);
    }

    @Override
    public Order modify(OrderDTO dto, Integer id) {
        OrderChecks.modifyCheck(dto,id);
        Order existing = orderRepository.retrieveById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order with id " + id + " does not exist"));
        if (dto.getUserId() != null) {
            existing.setUserId(dto.getUserId());
        }
        if (dto.getTotalAmount() != null) {
            if (dto.getTotalAmount() <= 0) {
                throw new IllegalArgumentException("Total amount must be greater than 0");
            }
            existing.setTotalAmount(BigDecimal.valueOf(dto.getTotalAmount()));
        }
        if (dto.getStatus() != null) {
            existing.setStatus(dto.getStatus());
        }
        return orderRepository.update(existing);
    }

    @Override
    public long count() {
        return orderRepository.count();
    }

    @Override
    public List<Order> findByUserId(Integer userId) {
       OrderChecks.findByUserIdCheck(userId);
        return orderRepository.retrieveByUserId(userId);
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
       OrderChecks.findByStatusCheck(status);
        return orderRepository.retrieveByStatus(status);
    }

    @Override
    public List<Order> findByOrderDateAfter(LocalDateTime date) {
       OrderChecks.findByOrderDateAfterCheck(date);
        return orderRepository.retrieveByOrderDateAfter(date);
    }

    @Override
    public List<Order> findByTotalAmountGreaterThan(Double amount) {
       OrderChecks.findBYTotalAmountGreaterThanCheck(amount);
        return orderRepository.retrieveByTotalAmountGreaterThan(amount);
    }
}
