package uz.kruz.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uz.kruz.domain.vo.UserRole;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
public class UserDTO extends BaseDTO {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
    private UserRole role;

    @Override
    public String toString() {
        return "UserDTO{" + "id=" + getId() + ", fullName='" + fullName + '\'' + ", email='" + email + '\'' + ", phoneNumber='" + phoneNumber + '\'' + ", role=" + role.name() + ", createdAt=" + getCreatedAt() + ", updatedAt=" + getUpdatedAt() + '}';
    }
}
