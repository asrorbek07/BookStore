package uz.kruz.repository.impl;

import uz.kruz.db.DatabaseConnection;
import uz.kruz.domain.Author;
import uz.kruz.repository.AuthorRepository;
import uz.kruz.service.AuthorService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorRepositoryImpl implements AuthorRepository {

    private final Connection connection;

    public AuthorRepositoryImpl() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    @Override
    public Author create(Author entity) {
        String sql = "insert into author (full_name) values (?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1,entity.getFullName());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()){
                entity.setId(rs.getInt(1));
            }
            return entity;

        }catch(SQLException e){
            throw new RuntimeException("Error creating entity",e);
        }

    }

    @Override
    public Optional<Author> retrieveById(Integer id) {
        String sql = "SELECT * FROM authors WHERE id = ? ";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery(sql);
            if(rs.next()){
                return rs.getLong(1);
            }
        }catch (SQLException e){
            throw new RuntimeException("Error retrieving entity",e);
        }
        return 0;
    }

    @Override
    public List<Author> retrieveAll(String fullName) {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM authors";
        try (Statement stms = connection.prepareStatement(sql)) {
            ResultSet rs = stms.executeQuery(sql);
            while (rs.next()) {
                authors.add(mapRow(sql));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving entity", e);
        }
        return authors;

    }

    @Override
    public boolean deleteById(Integer id) {
        String sql  = "DELETE FROM authors WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1,id);
            return ps.executeUpdate() > 0;
        }catch (SQLException e){
            throw new RuntimeException("Error deleting entity",e);
        }
    }

    @Override
    public Author update(Author entity) {
        String sql = "UPDATE authors SET full_name = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,entity.getFullName());
            ps.setInt(2, entity.getId());
            ps.executeUpdate();
            return entity;
        }catch (SQLException e){
            throw new RuntimeException("Error updating entity",e);
        }

    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM authors";
        try (Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                return rs.getLong(1);
            }

        }catch (SQLException e){
            throw new RuntimeException("Error counting entity",e);
        }
        return 0;
    }

    @Override
    public List<Author> retrieveByName(String name) {
        List<Author> authors = new ArrayList<>();
        String  sql =  "SELECT * FROM authors WHERE full_name LIKE ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1,"%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                authors.add(mapRow(rs));

            }
        }catch (SQLException e){
            throw new RuntimeException("Error retrieving entity",e);
        }

        return authors;
    }

    @Override
    public List<Author> retrieveByBookId(Integer bookId) {

        String sql = "SELECT * FROM authors WHERE book_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1,bookId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return Optional.of(mapRow(rs));
            }
        }catch (SQLException e){
            throw new RuntimeException("Error retrieving entity",e);
        }
        return Optional.empty();
    }
}
