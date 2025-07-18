package uz.kruz.util.orderItemCheck;

import uz.kruz.dto.OrderItemDTO;

public class OrderItemChecks {
    public static void registerCheck(OrderItemDTO dto){
        if (dto.getOrderId() == null) {
            throw new IllegalArgumentException("Order id is required");
        }
        if (dto.getBookId() == null) {
            throw new IllegalArgumentException("Book id is required");
        }
        if (dto.getQuantity() == null || dto.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (dto.getPrice() == null || dto.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
    }
    public static void findByIdCheck(Integer id){
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id must be positive");
        }
    }
    public static void removeByIdCheck(Integer id){
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id must be positive");
        }
    }
    public static void modifyCheck(OrderItemDTO dto){
        if (dto.getOrderId() == null) {
            throw new IllegalArgumentException("Order id is required");
        }
        if (dto.getBookId() == null) {
            throw new IllegalArgumentException("Book id is required");
        }
        if (dto.getQuantity() == null || dto.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (dto.getPrice() == null || dto.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
    }
    public static void findByOrderIdCheck(Integer orderId){
        if (orderId == null || orderId <= 0) {
            throw new IllegalArgumentException("Order id must be positive");
        }
    }
    public static void findByBookIdCheck(Integer bookId){
        if (bookId == null || bookId <= 0) {
            throw new IllegalArgumentException("Book id must be positive");
        }
    }
    public static void findByQuantityCheck(Integer quantity){
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
    }
}
