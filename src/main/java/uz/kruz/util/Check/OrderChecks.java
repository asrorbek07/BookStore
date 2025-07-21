package uz.kruz.util.Check;

import uz.kruz.domain.vo.OrderStatus;
import uz.kruz.dto.OrderDTO;

import java.time.LocalDateTime;

public class OrderChecks {
    public static void registerCheck(OrderDTO dto){
        if (dto.getUserId() == null) {
            throw new IllegalArgumentException("User ID is required");
        }
        if (dto.getTotalAmount() == null) {
            throw new IllegalArgumentException("Total amount is required");
        }
        if (dto.getStatus() == null) {
            throw new IllegalArgumentException("Status is required");
        }
    }
    public static void findByIdCheck(Integer id){
        if (id == null) {
            throw new IllegalArgumentException("Order ID is required");
        }
    }
    public static void removeByIdCheck(Integer id){
        if (id == null) {
            throw new IllegalArgumentException("Order ID is required");
        }
    }
    public static void modifyCheck(OrderDTO dto,  Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Order ID is required for modification");
        }
    }
    public static void findByUserIdCheck(Integer userId){
        if (userId == null) {
            throw new IllegalArgumentException("User ID is required");
        }
    }
    public static void findByStatusCheck(OrderStatus status){
        if (status == null) {
            throw new IllegalArgumentException("Status is required");
        }
    }
    public static void findByOrderDateAfterCheck(LocalDateTime date){
        if (date == null) {
            throw new IllegalArgumentException("Date is required");
        }
    }
    public static void findBYTotalAmountGreaterThanCheck(Double amount){
        if (amount == null) {
            throw new IllegalArgumentException("Amount is required");
        }
    }
}
