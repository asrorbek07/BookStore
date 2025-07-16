package uz.kruz;

import uz.kruz.domain.Order;
import uz.kruz.domain.vo.OrderStatus;
import uz.kruz.repository.UserRepository;
import uz.kruz.repository.impl.OrderRepositoryImpl;
import uz.kruz.repository.impl.UserRepositoryImpl;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.time.LocalDateTime;

/**
 * Main class for the Bookstore application
 */
public class Main {

    public static void main(String[] args) {
        // Create and run the BookStoreAdmin
//        BookStoreAdmin admin = new BookStoreAdmin();
//        admin.run();
//
        OrderRepositoryImpl orderRepository = new OrderRepositoryImpl();
//        Order order = new Order();
//        order.setUserId(3);
//        order.setTotalAmount(new BigDecimal(222.36));
//        order.setId(6);
//        order.setStatus(OrderStatus.valueOf("PENDING"));


//        System.out.println(orderRepository.create(order));
//        System.out.println(orderRepository.retrieveById(32));
//          orderRepository.retrieveAll().forEach(order -> {
//            System.out.println(order.getId());
//            System.out.println(order.getUserId());
//              System.out.println(order.getTotalAmount());
//              System.out.println(order.getStatus());
//              System.out.println(order.getOrderDate());
//              System.out.println(order.getUpdatedAt()+ "\n-----------------------------------");
//        });
//        System.out.println(orderRepository.deleteById(32));
//        System.out.println(orderRepository.update(order));
//        System.out.println(orderRepository.count());
//        System.out.println(orderRepository.retrieveByUserId(3));
//        System.out.println(orderRepository.retrieveByStatus(OrderStatus.PENDING));
//        System.out.println(orderRepository.retrieveByOrderDateAfter(LocalDateTime.parse("2025-07-18T14:00:00")));
        System.out.println(orderRepository.retrieveByTotalAmountGreaterThan(30.0));


//        System.out.println(order);
//        OrderRepositoryImpl orderRepository = new OrderRepositoryImpl();
//        orderRepository.count();


//        UserRepositoryImpl userRepository = new UserRepositoryImpl();
//        userRepository.retrieveAll().forEach(
//                user -> {
//                    System.out.println("User ID: " + user.getId());
//                    System.out.println("User Name: " + user.getFullName());
//                    System.out.println("User Email: " + user.getEmail());
//                    System.out.println("User Role: " + user.getRole());
//                    System.out.println("-----------------------------");
//
//                }
//        );

    }
}
