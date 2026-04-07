package com.gk_dreams.HLuxe.dto;

import com.gk_dreams.HLuxe.entity.*;
import com.gk_dreams.HLuxe.enums.BookingStatus;
import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDto {
    private Long id;

    private Integer roomsCount;

    private LocalDate checkInDate;

    private LocalDate checkoutDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private BookingStatus bookingStatus;

    private Set<GuestDto> guests;

    private BigDecimal amount;
}
