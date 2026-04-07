package com.gk_dreams.HLuxe.service;

import com.gk_dreams.HLuxe.dto.RoomDto;
import com.gk_dreams.HLuxe.entity.Hotel;
import com.gk_dreams.HLuxe.entity.Room;
import com.gk_dreams.HLuxe.exceptions.ResourceNotFoundException;
import com.gk_dreams.HLuxe.repository.HotelRepository;
import com.gk_dreams.HLuxe.repository.InventoryRepository;
import com.gk_dreams.HLuxe.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;

    private final HotelRepository hotelRepository;

    private final ModelMapper modelMapper;

    private final InventoryService inventoryService;

    @Override
    public RoomDto createNewRoom(Long hotelId, RoomDto roomDto) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(
                ()-> new ResourceNotFoundException("Hotel Not found with Id: "+hotelId));
        Room room = modelMapper.map(roomDto, Room.class);
        room.setHotel(hotel);
        room = roomRepository.save(room);
        if(hotel.getActive()){
            inventoryService.initialiseRoomForAYear(room);
        }
        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public List<RoomDto> getALlRooms(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(
                ()-> new ResourceNotFoundException("Hotel Not found with Id: "+hotelId));
        return hotel.getRooms()
                .stream()
                .map(element -> modelMapper.map(element, RoomDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoom(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(
                ()-> new ResourceNotFoundException("Room Not found with Id: "+roomId));
        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    @Transactional
    public void deleteRoom(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(
                ()-> new ResourceNotFoundException("Room Not found with Id: "+roomId));
        inventoryService.deleteAllInventories(room);
        roomRepository.deleteById(roomId);
    }
}
