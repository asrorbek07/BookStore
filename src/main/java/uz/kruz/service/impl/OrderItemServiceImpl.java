package uz.kruz.service.impl;

import uz.kruz.domain.OrderItem;
import uz.kruz.dto.OrderItemDTO;
import uz.kruz.repository.BookRepository;
import uz.kruz.repository.OrderItemRepository;
import uz.kruz.service.OrderItemService;
import uz.kruz.util.Validator;
import uz.kruz.util.exceptions.EntityNotFoundException;
import uz.kruz.util.exceptions.ServiceException;

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

        if (dto == null) {
            throw new ServiceException("OrderItemDTO must not be null");
        }
        Validator.validateInteger(dto.getOrderId(), "OrderID");
        Validator.validateInteger(dto.getBookId(), "BookID");
        Validator.validateInteger(dto.getQuantity(), "Quantity");
        Validator.validateBigDecimal(BigDecimal.valueOf(dto.getPrice()), "Price");

        OrderItem orderItem = OrderItem.builder()
                .orderId(dto.getOrderId())
                .bookId(dto.getBookId())
                .quantity(dto.getQuantity())
                .price(BigDecimal.valueOf(dto.getPrice())).build();
        return orderItemRepository.create(orderItem);
    }

    @Override
    public Optional<OrderItem> findById(Integer id) {
        Validator.validateInteger(id, "ID");
        return orderItemRepository.retrieveById(id);
    }

    @Override
    public List<OrderItem> findAll() {
        return  orderItemRepository.retrieveAll();
    }

    @Override
    public boolean removeById(Integer id) {
        Validator.validateInteger(id, "ID");
        return orderItemRepository.deleteById(id);
    }

    @Override
    public OrderItem modify(OrderItemDTO dto, Integer id) {
        Validator.validateInteger(id, "ID");
        OrderItem existing = orderItemRepository.retrieveById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem with id " + id + " does not exist"));

        boolean modified = false;
        if (dto.getOrderId() != null) {
            Validator.validateInteger(dto.getOrderId(), "OrderID");
            existing.setOrderId(dto.getOrderId());
            modified = true;
        }
        if(dto.getBookId() != null) {
            Validator.validateInteger(dto.getBookId(), "BookID");
            existing.setBookId(dto.getBookId());
            modified = true;
        }
        if (dto.getQuantity()!=null) {
            Validator.validateInteger(dto.getQuantity(), "Quantity");
            existing.setQuantity(dto.getQuantity());
            modified = true;
        }
        if (dto.getPrice()!=null) {
            Validator.validateBigDecimal(BigDecimal.valueOf(dto.getPrice()), "Price");
            existing.setPrice(BigDecimal.valueOf(dto.getPrice()));
            modified = true;
        }
        if (!modified) {
            throw new ServiceException("No fields provided for update");
        }
        return orderItemRepository.update(existing);
    }

    @Override
    public long count() {
        return orderItemRepository.count();
    }

    @Override
    public boolean existsById(Integer integer) {
        return orderItemRepository.existsById(integer);
    }

    @Override
    public List<OrderItem> findByOrderId(Integer orderId) {
        Validator.validateInteger(orderId, "OrderID");
        return orderItemRepository.retrieveByOrderId(orderId);
    }

    @Override
    public List<OrderItem> findByBookId(Integer bookId) {
        Validator.validateInteger(bookId, "BookID");
        return orderItemRepository.retrieveByBookId(bookId);
    }

    @Override
    public List<OrderItem> findByQuantityGreaterThan(Integer quantity) {
        Validator.validateInteger(quantity, "Quantity");
        return orderItemRepository.retrieveByQuantityGreaterThan(quantity);
    }


}
