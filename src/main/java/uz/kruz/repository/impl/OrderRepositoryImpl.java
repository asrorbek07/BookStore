package uz.kruz.repository.impl;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.Order;
import uz.kruz.domain.vo.OrderStatus;
import uz.kruz.repository.OrderRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {

    private final Connection connection;

    public OrderRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    @Override
    public Order create(Order entity) {   //👍
        String sql = "insert into orders(user_id, total_amount, status) values (?, ?, ?)";
        try(PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, entity.getUserId());
            ps.setBigDecimal(2, entity.getTotalAmount());
            ps.setString(3, entity.getStatus().name());

//            ps.setTimestamp(4, entity.getOrderDate());
//            ps.setDate(5,entity.getUpdatedAt());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                entity.setId(rs.getInt(1));
            }
            return entity;

        }catch (SQLException e){
            throw new UnsupportedOperationException("Method not implemented");

        }
    }

    @Override
    public Optional<Order> retrieveById(Integer id) { //👍
        String sql = "SELECT * FROM orders WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return Optional.of(mapRow(rs));

            }
        }catch (SQLException e){

            throw new UnsupportedOperationException("Method not implemented");
        }
        return Optional.empty();
    }

    @Override
    public List<Order> retrieveAll() { // 👍
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try(Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                orders.add(mapRow(rs));
            }
        } catch (SQLException e) {

            throw new UnsupportedOperationException("Method not implemented");
        }
        return orders;
    }

    @Override
    public boolean deleteById(Integer id) { // 👍
        String sql = "DELETE FROM orders WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }catch (SQLException e) {

            throw new UnsupportedOperationException("Method not implemented");
        }
    }

    @Override
    public Order update(Order entity) { // 👍
        String sql = "update orders set user_id = ?, total_amount = ?, status = ? where id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, entity.getUserId());
            ps.setBigDecimal(2, entity.getTotalAmount());
            ps.setString(3, entity.getStatus().name());
            ps.setInt(4, entity.getId());
            ps.executeUpdate();
        } catch(SQLException e) {
            throw new UnsupportedOperationException("Method not implemented");
        }
        return entity;
    }

    @Override
    public long count() { // 👍
        String sql = "select count(*) from orders";
        try(Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {                             // ??????????
                return rs.getLong(1);       // ????????
            }
        }catch (SQLException e){
            throw new UnsupportedOperationException("Method not implemented");
        }

        return 0;  // ???????????
    }

    @Override
    public List<Order> retrieveByUserId(Integer userId) { //👍
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                orders.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new UnsupportedOperationException("Method not implemented");
        }
        return orders;
    }

    @Override
    public List<Order> retrieveByStatus(OrderStatus status) { //👍
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE status = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status.name());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                orders.add(mapRow(rs));
            }
        }catch (SQLException e) {
            throw new UnsupportedOperationException("Method not implemented");
        }
        return orders;
    }

    @Override
    public List<Order> retrieveByOrderDateAfter(LocalDateTime date) { // 👍
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE order_date < ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(date));
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                orders.add(mapRow(rs));
            }
        }catch (SQLException e) {
            throw new UnsupportedOperationException("Method not implemented");
        }
        return orders;
    }

    @Override
    public List<Order> retrieveByTotalAmountGreaterThan(Double amount) { // 👍
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE total_amount > ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBigDecimal(1, BigDecimal.valueOf(amount));
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                orders.add(mapRow(rs));
            }
        }catch (SQLException e){
            throw new UnsupportedOperationException("Method not implemented");
        }
        return orders;
    }

    private Order mapRow(ResultSet rs) throws SQLException {
        return Order.builder()
                .id(rs.getInt("id"))
                .userId(rs.getInt("user_id"))
                .totalAmount(rs.getBigDecimal("total_amount"))
                .status(OrderStatus.valueOf(rs.getString("status")))
                .build();
    }
//public static void main(String[] args) {
//
//}
}
