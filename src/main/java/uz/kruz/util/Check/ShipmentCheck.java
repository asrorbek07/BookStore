package uz.kruz.util.Check;

import uz.kruz.dto.ShipmentDTO;

import java.time.LocalDateTime;

public class ShipmentCheck {
    public static void registerCheck(ShipmentDTO dto){
        if (dto.getOrderId() == null) {
            throw new IllegalArgumentException("Order ID is required");
        }
        if (dto.getTrackingNo() == null) {
            throw new IllegalArgumentException("Tracking No is required");
        }
        if (dto.getShippedAt() == null) {
            throw new IllegalArgumentException("Shipped At is required");
        }
        if (dto.getDeliveryEstimate() == null) {
            throw new IllegalArgumentException("Delivery Estimate is required");
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
    public static void modifyCheck(ShipmentDTO dto,  Integer id){
        if (dto.getOrderId() == null) {
            throw new IllegalArgumentException("Order ID is required");
        }
        if (dto.getTrackingNo() == null) {
            throw new IllegalArgumentException("Tracking No is required");
        }
        if (dto.getShippedAt() == null) {
            throw new IllegalArgumentException("Shipped At is required");
        }
        if (dto.getDeliveryEstimate() == null) {
            throw new IllegalArgumentException("Delivery Estimate is required");
        }
    }
    public static void findByTrackingNumberCheck(String trackingNumber){
        if (trackingNumber == null || trackingNumber.isEmpty()) {
            throw new IllegalArgumentException("trackingNumber is required");
        }
    }
    public static void findByOrderIdCheck(Integer orderId){
        if (orderId == null || orderId <= 0) {
            throw new IllegalArgumentException("Order ID is required");
        }
    }
    public static void findByShippedAtCheck(LocalDateTime date){
        if (date == null) {
            throw new IllegalArgumentException("date is required");
        }
    }
    public static void findByDeliveryEstimateCheck(LocalDateTime date){
        if (date == null) {
            throw new IllegalArgumentException("date is required");
        }
    }
}
