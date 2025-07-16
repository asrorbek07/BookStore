package uz.kruz.repository.impl;

import org.mariadb.jdbc.Statement;
import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.Category;
import uz.kruz.repository.CategoryRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryRepositoryImpl implements CategoryRepository {

    private final Connection connection;
    private final String CREATECATEGORY = "INSERT INTO categories (name) VALUES (?)";
    private final String RERIEVEBYIDD = "SELECT * FROM categories WHERE id=?";
    private final String RETRIEVEALLL = "SELECT * FROM categories";
    private final String DELETE = "DELETE FROM categories WHERE id=?";
    private final String UPDATECAT = "UPDATE categories SET name=? WHERE id=?";
    private final String COUNTCAT = "SELECT COUNT(*) FROM categories";
    private final String RETRIEVENAME = "SELECT * FROM categories WHERE name=?";

    public CategoryRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    @Override
    public Category create(Category entity) {

        try (PreparedStatement ps = connection.prepareStatement(CREATECATEGORY, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getName());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getInt(1));
            }
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Category> retrieveById(Integer id) {

        try (PreparedStatement ps = connection.prepareStatement(RERIEVEBYIDD)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Category> retrieveAll() {
        List<Category> list = new ArrayList<>();

        try (Statement stmt = (Statement) connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(RETRIEVEALLL);
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public boolean deleteById(Integer id) {

        try (PreparedStatement ps = connection.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Category update(Category entity) {

        try (PreparedStatement ps = connection.prepareStatement(UPDATECAT)) {
            ps.setString(1, entity.getName());
            ps.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public long count() {

        try (Statement stmt = (Statement) connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(COUNTCAT);
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting users", e);
        }
        return 0;
    }

    @Override
    public Optional<Category> retrieveByName(String name) {

        try (PreparedStatement ps = connection.prepareStatement(RETRIEVENAME)) {
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving users by name", e);
        }
        return Optional.empty();
    }

    private Category mapRow(ResultSet rs) throws SQLException {
        return Category.builder().name(rs.getString("name")).id(rs.getInt("id")).build();
    }

    public static void main(String[] args) {
        CategoryRepositoryImpl categoryRepository = new CategoryRepositoryImpl();
//        categoryRepository.retrieveAll().forEach(System.out::println);
//        System.out.println(categoryRepository.create(Category.builder().id(1).build()).toString());
        Category category = categoryRepository.retrieveById(3).get();

        System.out.println(category.getName());
        System.out.println(categoryRepository.count());

    }

}



