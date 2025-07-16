package uz.kruz.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import uz.kruz.domain.vo.BaseEntity;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
public class Review extends BaseEntity {
    private Integer userId;
    private Integer bookId;
    private Integer rating;  // Between 1 and 5
    private String comment;
    private LocalDateTime reviewedAt; // Maps to createdAt in BaseEntity

    // Relationships (not stored in database directly)
    private User user;
    private Book book;


}
