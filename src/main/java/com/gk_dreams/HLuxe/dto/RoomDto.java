package com.gk_dreams.HLuxe.dto;

import com.gk_dreams.HLuxe.entity.Hotel;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RoomDto {
    private Long id;

    private String type;

    private BigDecimal basePrice;

    private String[] photos;

    private String[] amenities;

    private Integer totalCount;

    private Integer capacity;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}