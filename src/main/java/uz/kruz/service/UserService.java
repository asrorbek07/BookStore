package uz.kruz.service;

import uz.kruz.dto.BaseDTO;
import uz.kruz.domain.vo.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserService<D extends BaseDTO> extends Service<D, Integer> {

    Optional<D> findByEmail(String email);

    List<D> findByName(String name);

    List<D> findByRole(UserRole role);
}
