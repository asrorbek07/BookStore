package uz.kruz.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
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
    private LocalDateTime reviewedAt;

    private User user;
    private Book book;

    @Override
    public String toString() {
        return "Review{" +
                "id=" + getId() +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", reviewedAt=" + reviewedAt +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }

}
