package uz.kruz;

import uz.kruz.repository.UserRepository;
import uz.kruz.repository.impl.OrderRepositoryImpl;
import uz.kruz.repository.impl.UserRepositoryImpl;

/**
 * Main class for the Bookstore application
 */
public class Main {

    public static void main(String[] args) {
        // Create and run the BookStoreAdmin
//        BookStoreAdmin admin = new BookStoreAdmin();
//        admin.run();
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        System.out.println(userRepository.retrieveAll());
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
