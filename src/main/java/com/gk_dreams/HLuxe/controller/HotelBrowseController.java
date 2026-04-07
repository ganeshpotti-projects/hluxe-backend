package com.gk_dreams.HLuxe.controller;

import com.gk_dreams.HLuxe.dto.HotelDto;
import com.gk_dreams.HLuxe.dto.HotelInfoDto;
import com.gk_dreams.HLuxe.dto.HotelPriceDto;
import com.gk_dreams.HLuxe.dto.HotelSearchRequest;
import com.gk_dreams.HLuxe.service.HotelService;
import com.gk_dreams.HLuxe.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelBrowseController {

    private final InventoryService inventoryService;

    private final HotelService hotelService;

    @GetMapping("/search")
    public ResponseEntity<Page<HotelPriceDto>> searchHotels(@RequestBody HotelSearchRequest hotelSearchRequest){
        Page<HotelPriceDto> page = inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable Long hotelId){
        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }
}
