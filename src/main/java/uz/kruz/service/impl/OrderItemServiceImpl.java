package uz.kruz.service.impl;

import uz.kruz.domain.OrderItem;
import uz.kruz.dto.OrderItemDTO;
import uz.kruz.repository.OrderItemRepository;
import uz.kruz.service.OrderItemService;
import uz.kruz.util.orderItemCheck.OrderItemChecks;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem register(OrderItemDTO dto) {

        OrderItemChecks.registerCheck(dto);

        OrderItem orderItem = OrderItem.builder()
                .orderId(dto.getOrderId())
                .bookId(dto.getBookId())
                .quantity(dto.getQuantity())
                .price(BigDecimal.valueOf(dto.getPrice())).build();
        return orderItemRepository.create(orderItem);
    }

    @Override
    public Optional<OrderItem> findById(Integer id) {
        OrderItemChecks.findByIdCheck(id);
        return orderItemRepository.retrieveById(id);
    }

    @Override
    public List<OrderItem> findAll() {
        return  orderItemRepository.retrieveAll();
    }

    @Override
    public boolean removeById(Integer id) {
        OrderItemChecks.removeByIdCheck(id);
        return orderItemRepository.deleteById(id);
    }

    @Override
    public OrderItem modify(OrderItemDTO dto, Integer id) {
        OrderItemChecks.modifyCheck(dto);
        Optional<OrderItem> existing = orderItemRepository.retrieveById(id);
        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + dto.getQuantity());
        }
        return orderItemRepository.update(existing.get());
    }

    @Override
    public long count() {
        return orderItemRepository.count();
    }

    @Override
    public List<OrderItem> findByOrderId(Integer orderId) {
        OrderItemChecks.findByOrderIdCheck(orderId);
        return orderItemRepository.retrieveByOrderId(orderId);
    }

    @Override
    public List<OrderItem> findByBookId(Integer bookId) {
        OrderItemChecks.findByBookIdCheck(bookId);
        return orderItemRepository.retrieveByBookId(bookId);
    }

    @Override
    public List<OrderItem> findByQuantityGreaterThan(Integer quantity) {
        OrderItemChecks.findByQuantityCheck(quantity);
        return orderItemRepository.retrieveByQuantityGreaterThan(quantity);
    }


}
