package uz.kruz.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import uz.kruz.domain.vo.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
public class Publisher extends BaseEntity {
    private String name;
    private String contactEmail;
    private String phone;

}
