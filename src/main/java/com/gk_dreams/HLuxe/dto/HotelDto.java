package com.gk_dreams.HLuxe.dto;

import com.gk_dreams.HLuxe.entity.HotelContactInfo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HotelDto {
    private Long id;

    private String name;

    private String city;

    private String[] photos;

    private String[] amenities;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private HotelContactInfo contactInfo;

    private Boolean active;
}
