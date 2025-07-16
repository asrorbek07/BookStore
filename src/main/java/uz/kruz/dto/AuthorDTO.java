package uz.kruz.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
public class AuthorDTO extends BaseDTO {
    private String fullName;
    private List<Integer> bookIds; // Only store IDs of related books, not the full objects

}