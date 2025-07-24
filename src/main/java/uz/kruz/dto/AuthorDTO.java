package uz.kruz.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
public class AuthorDTO extends BaseDTO {
    private String fullName;
    private List<Integer> bookIds;

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "id=" + getId() +
                ", fullName='" + fullName + '\'' +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }

}