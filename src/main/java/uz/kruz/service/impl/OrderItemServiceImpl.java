package uz.kruz.service.impl;

import uz.kruz.domain.OrderItem;
import uz.kruz.dto.OrderItemDTO;
import uz.kruz.repository.BookRepository;
import uz.kruz.repository.OrderItemRepository;
import uz.kruz.service.OrderItemService;
import uz.kruz.util.StringUtil;
import uz.kruz.util.orderItemCheck.OrderItemChecks;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final BookRepository bookRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, BookRepository bookRepository) {
        this.orderItemRepository = orderItemRepository;
        this.bookRepository = bookRepository;
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
        OrderItem existing = orderItemRepository.retrieveById(id).orElseThrow(() -> new IllegalArgumentException("OrderItem with id " + id + " does not exist"));
        OrderItemChecks.modifyCheck(dto);
        if(dto.getBookId() != null) {
            bookRepository.retrieveById(dto.getBookId()).orElseThrow(() -> new IllegalArgumentException("Book with id " + dto.getBookId() + " does not exist"));
        }
        if (dto.getQuantity()!=null) {
            if (dto.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0");
            }
            existing.setQuantity(dto.getQuantity());
        }
        if (dto.getPrice()!=null) {
            if (dto.getPrice() <= 0) {
                throw new IllegalArgumentException("Price must be greater than 0");
            }
            existing.setPrice(BigDecimal.valueOf(dto.getPrice()));
        }
        return orderItemRepository.update(existing);
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
