package com.gk_dreams.HLuxe.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Builder
public class HotelContactInfo {
    private String address;

    private String phoneNumber;

    private String email;

    private String location;
}
