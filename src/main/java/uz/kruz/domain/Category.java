package uz.kruz.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uz.kruz.domain.vo.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
public class Category extends BaseEntity {
    private String name;

    @Override
    public String toString() {
        return "Category{" +
                "id=" + getId() +
                "name='" + name + '\'' +
                "createdAt=" + getCreatedAt() +
                "updatedAt=" + getUpdatedAt() +
                '}';
    }
}
