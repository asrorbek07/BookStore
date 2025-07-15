package uz.kruz.repository.impl;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.OrderItem;
import uz.kruz.repository.OrderItemRepository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class OrderItemRepositoryImpl implements OrderItemRepository {

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
        String sql = "INSERT INTO order_items(order_id, book_id, quantity, price) VALUES(?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
            throw new UnsupportedOperationException("Method not implemented");
        }

    }

    @Override
    public Optional<OrderItem> retrieveById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<OrderItem> retrieveAll() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public boolean deleteById(Integer id) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public OrderItem update(OrderItem entity) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<OrderItem> retrieveByOrderId(Integer orderId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<OrderItem> retrieveByBookId(Integer bookId) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public List<OrderItem> retrieveByQuantityGreaterThan(Integer quantity) {
        throw new UnsupportedOperationException("Method not implemented");
    }
}
