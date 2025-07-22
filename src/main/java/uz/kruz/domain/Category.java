package uz.kruz.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import uz.kruz.domain.vo.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
@ToString(callSuper = true)
public class Category extends BaseEntity {
    private String name;
}
