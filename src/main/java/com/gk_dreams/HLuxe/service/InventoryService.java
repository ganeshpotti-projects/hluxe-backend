package com.gk_dreams.HLuxe.service;

import com.gk_dreams.HLuxe.dto.HotelDto;
import com.gk_dreams.HLuxe.dto.HotelSearchRequest;
import com.gk_dreams.HLuxe.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {
    void initialiseRoomForAYear(Room room);

    void deleteAllInventories(Room room);

    Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
