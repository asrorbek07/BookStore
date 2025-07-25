package uz.kruz.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
public class CategoryDTO extends BaseDTO {
    private String name;

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }

}
