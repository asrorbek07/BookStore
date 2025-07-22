package uz.kruz.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
public class PublisherDTO extends BaseDTO {
    private String name;
    private String contact_email;
    private String phone;
}
