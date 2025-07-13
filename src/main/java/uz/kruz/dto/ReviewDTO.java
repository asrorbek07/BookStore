package uz.kruz.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@SuperBuilder
public class ReviewDTO extends BaseDTO {
    private Integer userId;
    private Integer bookId;
    private Integer rating;
    private String comment;

    @Override
    public String toString() {
        return "ReviewDTO{" +
                "id=" + getId() +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}
