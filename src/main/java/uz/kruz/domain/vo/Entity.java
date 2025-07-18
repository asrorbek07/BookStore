package uz.kruz.domain.vo;

import java.time.LocalDateTime;


public interface Entity {
    Integer getId();

    void setId(Integer id);

    LocalDateTime getCreatedAt();

    void setCreatedAt(LocalDateTime createdAt);

    LocalDateTime getUpdatedAt();

    void setUpdatedAt(LocalDateTime updatedAt);

}