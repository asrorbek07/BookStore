package uz.kruz.service;

import uz.kruz.domain.User;
import uz.kruz.domain.vo.UserRole;
import uz.kruz.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService extends Service<UserDTO, User, Integer> {

    Optional<User> findByEmail(String email);

    List<User> findByName(String name);

    List<User> findByRole(UserRole role);
}
