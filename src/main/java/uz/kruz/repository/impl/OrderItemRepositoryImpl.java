package uz.kruz.repository.impl;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.OrderItem;
import uz.kruz.repository.OrderItemRepository;

import java.sql.*;
import java.util.ArrayList;
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
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new UnsupportedOperationException("Method not implemented");
        }
        return Optional.empty();
    }

    @Override
    public List<OrderItem> retrieveAll() {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM order_items";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                orderItems.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new UnsupportedOperationException("Method not implemented");
        }
        return orderItems;
        }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM order_items WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1,id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new UnsupportedOperationException("Method not implemented", e);
        }
    }

    @Override
    public OrderItem update(OrderItem entity) {
        String sql = "UPDATE order_items SET quantity = ?, price = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, entity.getQuantity());
            ps.setBigDecimal(2, entity.getPrice());
            ps.setInt(3, entity.getId());
            ps.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new UnsupportedOperationException("Method not implemented", e);
        }
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM order_items";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new UnsupportedOperationException("Method not implemented", e);
        }
        return 0;
    }

    @Override
    public List<OrderItem> retrieveByOrderId(Integer orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                orderItems.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new UnsupportedOperationException("Method not implemented", e);
        }
        return orderItems;
    }

    @Override
    public List<OrderItem> retrieveByBookId(Integer bookId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE book_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                orderItems.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new UnsupportedOperationException("Method not implemented", e);
        }
        return orderItems;
    }

    @Override
    public List<OrderItem> retrieveByQuantityGreaterThan(Integer quantity) {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE quantity > ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                orderItems.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new UnsupportedOperationException("Method not implemented", e);
        }
        return orderItems;
    }

    private OrderItem mapRow(ResultSet rs) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(rs.getInt("id"));
        orderItem.setOrderId(rs.getInt("order_id"));
        orderItem.setBookId(rs.getInt("book_id"));
        orderItem.setQuantity(rs.getInt("quantity"));
        orderItem.setPrice(rs.getBigDecimal("price"));
        return orderItem;
    }
}
