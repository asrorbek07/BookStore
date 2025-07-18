package uz.kruz.repository.impl;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.OrderItem;
import uz.kruz.repository.OrderItemRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderItemRepositoryImpl implements OrderItemRepository {
    private final String INSERT = "INSERT INTO order_items(order_id, book_id, quantity, price) VALUES(?, ?, ?, ?)";
    private final String SELECT_BY_ID = "SELECT * FROM order_items WHERE id = ?";
    private final String SELECT_ALL = "SELECT * FROM order_items";
    private final String DELETE = "DELETE FROM order_items WHERE id = ?";
    private final String UPDATE = "UPDATE order_items SET quantity = ?, price = ? WHERE id = ?";
    private final String COUNT = "SELECT COUNT(*) FROM order_items";
    private final String SELECT_BY_ORDER = "SELECT * FROM order_items WHERE order_id = ?";
    private final String SELECT_BY_BOOK = "SELECT * FROM order_items WHERE book_id = ?";
    private final String SELECT_BY_QUANTITY = "SELECT * FROM order_items WHERE quantity > ?";
    private final Connection connection;

    public OrderItemRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    @Override
    public OrderItem create(OrderItem entity) {

        try (PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, entity.getOrderId());
            ps.setInt(2, entity.getBookId());
            ps.setInt(3, entity.getQuantity());
            ps.setBigDecimal(4, entity.getPrice());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getInt(1));
            }
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting OrderItem", e);
        }

    }

    @Override
    public Optional<OrderItem> retrieveById(Integer id) {

        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving OrderItem by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<OrderItem> retrieveAll() {
        List<OrderItem> orderItems = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL);
            while (rs.next()) {
                orderItems.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all OrderItems", e);
        }
        return orderItems;
    }

    @Override
    public boolean deleteById(Integer id) {

        try (PreparedStatement ps = connection.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting OrderItem", e);
        }
    }

    @Override
    public OrderItem update(OrderItem entity) {

        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setInt(1, entity.getQuantity());
            ps.setBigDecimal(2, entity.getPrice());
            ps.setInt(3, entity.getId());
            ps.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating OrderItem", e);
        }
    }

    @Override
    public long count() {

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(COUNT);
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting OrderItems", e);
        }
        return 0;
    }

    @Override
    public List<OrderItem> retrieveByOrderId(Integer orderId) {
        List<OrderItem> orderItems = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_ORDER)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                orderItems.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving by orderId", e);
        }
        return orderItems;
    }

    @Override
    public List<OrderItem> retrieveByBookId(Integer bookId) {
        List<OrderItem> orderItems = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_BOOK)) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                orderItems.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving by bookId", e);
        }
        return orderItems;
    }

    @Override
    public List<OrderItem> retrieveByQuantityGreaterThan(Integer quantity) {
        List<OrderItem> orderItems = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_QUANTITY)) {
            ps.setInt(1, quantity);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                orderItems.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving by quantity", e);
        }
        return orderItems;
    }

    private OrderItem mapRow(ResultSet rs) throws SQLException {
        return OrderItem.builder()
                .id(rs.getInt("id"))
                .orderId(rs.getInt("order_id"))
                .bookId(rs.getInt("book_id"))
                .quantity(rs.getInt("quantity"))
                .price(rs.getBigDecimal("price"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                .build();
    }

//    public static void main(String[] args) {
//        OrderItemRepository orderItemRepository = new OrderItemRepositoryImpl();
//        orderItemRepository.retrieveAll().forEach(
//                orderItem -> {
//                    System.out.println("Order ID: " + orderItem.getId());
//                    System.out.println("Order Book ID: " + orderItem.getBookId());
//                    System.out.println("Order quantity: " + orderItem.getQuantity());
//                    System.out.println("Order price: " + orderItem.getPrice());
//                    System.out.println("-----------------------------");
//
//                }
//        );
//    }
}
