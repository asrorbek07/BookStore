package uz.kruz.repository;

import uz.kruz.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User, Integer> {

    Optional<User> retrieveByEmail(String email);

    List<User> retrieveByName(String name);

    List<User> retrieveByRole(uz.kruz.domain.vo.UserRole role);
}
