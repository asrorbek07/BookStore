package uz.kruz.service.impl;

import uz.kruz.domain.Order;
import uz.kruz.domain.vo.OrderStatus;
import uz.kruz.dto.OrderDTO;
import uz.kruz.repository.OrderRepository;
import uz.kruz.service.OrderService;
import uz.kruz.util.Validator;
import uz.kruz.util.exceptions.EntityNotFoundException;
import uz.kruz.util.exceptions.ServiceException;

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
        if (dto == null) {
            throw new ServiceException("OrderDTO must not be null");
        }
        Validator.validateInteger(dto.getUserId(),  "userId");
        Validator.validateBigDecimal(dto.getTotalAmount(), "totalAmount");
        if (dto.getStatus() == null) {
            throw new IllegalArgumentException("Status is required");
        }
        if (dto.getUserId() != null){
            Validator.validateInteger(dto.getUserId(), "userId");
        }
        if (dto.getTotalAmount() != null){
            Validator.validateBigDecimal(dto.getTotalAmount(), "totalAmount");
        }

        Order order = Order.builder()
                .userId(dto.getUserId())
                .totalAmount(dto.getTotalAmount())
                .status(dto.getStatus()).build();
        return orderRepository.create(order);

    }

    @Override
    public Optional<Order> findById(Integer id) {
        Validator.validateInteger(id, "id");
        return orderRepository.retrieveById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.retrieveAll();
    }

    @Override
    public boolean removeById(Integer id) {
        Validator.validateInteger(id, "id");
        return orderRepository.deleteById(id);
    }

    @Override
    public Order modify(OrderDTO dto, Integer id) {
        Validator.validateInteger(id, "id");

        Order existing = orderRepository.retrieveById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + id + " does not exist"));
        boolean modified = false;
//        if (dto.getUserId() != null) {
//            Validator.validateInteger(dto.getUserId(), "userId");
//            existing.setUserId(dto.getUserId());
//            modified = true;
//        }
        if (dto.getTotalAmount() != null) {
            Validator.validateBigDecimal(dto.getTotalAmount(), "totalAmount");
            existing.setTotalAmount(dto.getTotalAmount());
            modified = true;
        }
        if (dto.getStatus() != null) {
            existing.setStatus(dto.getStatus());
            modified = true;
        }
        if (!modified){
            throw new ServiceException("No fields were provided for update");
        }
        return orderRepository.update(existing);
    }

    @Override
    public long count() {
        return orderRepository.count();
    }

    @Override
    public boolean existsById(Integer integer) {
        return orderRepository.existsById(integer);
    }

    @Override
    public List<Order> findByUserId(Integer userId) {
       Validator.validateInteger(userId, "userId");
        return orderRepository.retrieveByUserId(userId);
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        if (status == null) {
            throw new ServiceException("Status is required");
        }
        return orderRepository.retrieveByStatus(status);
    }

    @Override
    public List<Order> findByOrderDateAfter(LocalDateTime date) {
        LocalDateTime now = LocalDateTime.now();
        if (date.isAfter(now)) {
            throw new ServiceException("Order cannot be in the future");
        }
        return orderRepository.retrieveByOrderDateAfter(date);
    }

    @Override
    public List<Order> findByTotalAmountGreaterThan(Double amount) {
        Validator.validateBigDecimal(BigDecimal.valueOf(amount), "totalAmount");
        return orderRepository.retrieveByTotalAmountGreaterThan(amount);
    }
}
