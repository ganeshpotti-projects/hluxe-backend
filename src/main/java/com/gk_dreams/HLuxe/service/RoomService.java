package com.gk_dreams.HLuxe.service;

import com.gk_dreams.HLuxe.dto.RoomDto;

import java.util.List;

public interface RoomService {
    RoomDto createNewRoom(Long hotelId, RoomDto roomDto);

    List<RoomDto> getAllRooms(Long hotelId);

    RoomDto getRoom(Long roomId);

    void deleteRoom(Long roomId);

}
