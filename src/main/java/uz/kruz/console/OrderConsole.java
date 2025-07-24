package uz.kruz.console;

import uz.kruz.domain.Order;
import uz.kruz.domain.OrderItem;
import uz.kruz.domain.vo.OrderStatus;
import uz.kruz.dto.OrderDTO;
import uz.kruz.dto.OrderItemDTO;
import uz.kruz.repository.impl.BookRepositoryImpl;
import uz.kruz.repository.impl.OrderItemRepositoryImpl;
import uz.kruz.repository.impl.OrderRepositoryImpl;
import uz.kruz.service.BookService;
import uz.kruz.service.OrderItemService;
import uz.kruz.service.OrderService;
import uz.kruz.service.impl.BookServiceImpl;
import uz.kruz.service.impl.OrderItemServiceImpl;
import uz.kruz.service.impl.OrderServiceImpl;
import uz.kruz.util.ConsoleUtil;
import uz.kruz.util.Narrator;
import uz.kruz.util.TalkingAt;
import uz.kruz.util.Validator;
import uz.kruz.util.exceptions.RepositoryException;
import uz.kruz.util.exceptions.ServiceException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderConsole {
    //
    private final OrderService orderService;
    private final ConsoleUtil consoleUtil;
    private final Narrator narrator;
    private final OrderItemService orderItemService;
    private final BookService bookService;

    public OrderConsole() {
        //
        this.orderService = new OrderServiceImpl(new OrderRepositoryImpl());
        this.orderItemService = new OrderItemServiceImpl(new OrderItemRepositoryImpl(), new BookRepositoryImpl());
        this.bookService = new BookServiceImpl(new BookRepositoryImpl());
        this.narrator = new Narrator(this, TalkingAt.Left);
        this.consoleUtil = new ConsoleUtil(narrator);
    }

    public void showAll() {
        //
        try {
            narrator.sayln("\n\t > All Orders: ");
            for (Order order : orderService.findAll()) {
                narrator.sayln("\t > " + order.toString());
            }
        } catch (ServiceException | RepositoryException e) {
            narrator.sayln(e.getMessage());
        }
    }
    public void register() {
        //
        while (true) {
            //
            Integer userId = consoleUtil.getValueOfInteger("\n(0. Order menu)\n Enter the user id: ");
            if (userId == 0) {
                return;
            }
            List<OrderItem> items = new ArrayList<>();
            BigDecimal total_amount = BigDecimal.ZERO;
            narrator.sayln("Add items to the order: ");
            while (true) {
                Integer bookId = consoleUtil.getValueOfInteger("Please enter the book id(0. return): ");
                if (bookId == 0) {break;}

                int quantity = consoleUtil.getValueOfInteger("Please enter the quantity: ");
                if (quantity <= 0) {
                    narrator.sayln("Quantity must be greater than 0.");
                    continue;
                }
                try {
                    BigDecimal price = bookService.getPriceById(bookId);
                    BigDecimal subTotal = price.multiply(new BigDecimal(quantity));
                    total_amount = total_amount.add(subTotal);

                    OrderItem item = OrderItem.builder()
                            .orderId(null)
                            .bookId(bookId)
                            .quantity(quantity)
                            .price(subTotal)
                            .build();

                    items.add(item);
                } catch (ServiceException e) {
                    narrator.sayln(e.getMessage());
                }
            }

            narrator.sayln("\nChoose order status: ");
            narrator.sayln("  1. PENDING");
            narrator.sayln("  2. CONFIRMED");
            narrator.sayln("  3. CANCELLED");

            int statusChoice = consoleUtil.getValueOfInteger("Enter your choice (1-3): ");
            OrderStatus status;
            switch (statusChoice) {
                case 1:
                    status = OrderStatus.PENDING;
                    break;
                case 2:
                    status = OrderStatus.CONFIRMED;
                    break;
                case 3:
                    status = OrderStatus.CANCELLED;
                    break;
                case 0: {
                    return;
                }
                default: {
                    narrator.sayln("Invalid choice! Please try again.");
                    continue;
                }
            }

            try {
                Validator.validateInteger(userId, "User ID");
                Validator.validateBigDecimal(total_amount, "Total amount");
                OrderDTO orderDTO = OrderDTO.builder()
                        .userId(userId)
                        .totalAmount(total_amount)
                        .status(status)
                        .build();
                Order registeredOrder = orderService.register(orderDTO);
                for (OrderItem item : items) {
                    item.setOrderId(registeredOrder.getId());

                    OrderItemDTO dto = OrderItemDTO.builder()
                            .orderId(item.getOrderId())
                            .bookId(item.getBookId())
                            .quantity(item.getQuantity())
                            .price(item.getPrice())
                            .build();

                    orderItemService.register(dto);
                }
                narrator.say("\n Registered Order: " + orderDTO.toString());
            } catch (ServiceException | RepositoryException | IllegalArgumentException e) {
                narrator.sayln(e.getMessage());
            }
        }
    }
    private Order findOne() {
        Order orderFound = null;
        while (true) {
            //
            Integer id = consoleUtil.getValueOfInteger("\n order id to find(0. Order menu): ");
            if (id == 0) {
                break;
            }

            try {
                Optional<Order> optionalOrder = orderService.findById(id);
                if (optionalOrder.isPresent()) {
                    orderFound = optionalOrder.get();
                    narrator.say("\n Order found: " + orderFound);
                    break;
                } else {
                    narrator.sayln("No order found with ID: " + id);
                }
                narrator.say("\n Order found: " + orderFound);
                break;
            } catch (ServiceException | RepositoryException e) {
                narrator.sayln(e.getMessage());
            }
        }
        return orderFound;
    }

    public void delete() {
        //
        Order order = findOne();
        if (order == null) {
            return;
        }
        String confirmStr = consoleUtil.getValueOf("\nDelete this Order? (Y:yes, N:no): ");
        if (confirmStr.equals("Y")) {
            try {
                orderService.removeById(order.getId());
                narrator.sayln("\n Order has been deleted.");
            }  catch (ServiceException | RepositoryException e) {
                narrator.sayln(e.getMessage());
            }

        }
    }
    public void modify(){
        //
        Order order = findOne();
        if (order == null) {
            return;
        }

        BigDecimal total_amount = consoleUtil.getValueOfBigDecimal("\nEnter the total amount: ");
        if (total_amount.equals(BigDecimal.ZERO)) {
            return;
        }
        narrator.sayln("\nChoose new order status (or 0 to cancel):");
        narrator.sayln("  1. PENDING");
        narrator.sayln("  2. CONFIRMED");
        narrator.sayln("  3. CANCELLED");

        int statusChoice = consoleUtil.getValueOfInteger("Enter your choice (1-3): ");
        if (statusChoice == 0) {
            return;
        }

        OrderStatus newStatus;
        switch (statusChoice) {
            case 1:
                newStatus = OrderStatus.PENDING;
                break;
            case 2:
                newStatus = OrderStatus.CONFIRMED;
                break;
            case 3:
                newStatus = OrderStatus.CANCELLED;
                break;
            default:
                narrator.sayln("Invalid choice! Order not modified.");
                return;
        }

        OrderDTO orderDTO = OrderDTO.builder()
                .totalAmount(total_amount)
                .status(newStatus)
                .build();
        try {
            orderService.modify(orderDTO, order.getId());
            narrator.say("\n Order has been modified.");
        }  catch (ServiceException | RepositoryException e) {
            narrator.sayln(e.getMessage());
        }
    }
}
