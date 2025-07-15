package uz.kruz;

import uz.kruz.repository.OrderItemRepository;
import uz.kruz.repository.impl.OrderItemRepositoryImpl;
import uz.kruz.repository.impl.UserRepositoryImpl;

/**
 * Main class for the Bookstore application
 */
public class Main {

    public static void main(String[] args) {
        // Create and run the BookStoreAdmin
//        BookStoreAdmin admin = new BookStoreAdmin();
//        admin.run();
//        UserRepositoryImpl userRepository = new UserRepositoryImpl();
//        userRepository.retrieveAll().forEach(
//                user -> {
//                    System.out.println("User ID: " + user.getId());
//                    System.out.println("User Name: " + user.getFullName());
//                    System.out.println("User Email: " + user.getEmail());
//                    System.out.println("User Role: " + user.getRole());
//                    System.out.println("-----------------------------");
//                }
//        );

        OrderItemRepository orderItemRepository = new OrderItemRepositoryImpl();
        orderItemRepository.retrieveAll().forEach(
                orderItem -> {
                    System.out.println("Order ID: " + orderItem.getId());
                    System.out.println("Order Book ID: " + orderItem.getBookId());
                    System.out.println("Order quantity: " + orderItem.getQuantity());
                    System.out.println("Order price: " + orderItem.getPrice());
                    System.out.println("-----------------------------");
                }
        );

    }
}
