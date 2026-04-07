package com.gk_dreams.HLuxe.dto;

import com.gk_dreams.HLuxe.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelPriceDto {

    private Hotel hotel;

    private Double price;
}
