package com.gk_dreams.HLuxe.service;

import com.gk_dreams.HLuxe.dto.HotelDto;
import com.gk_dreams.HLuxe.dto.HotelInfoDto;
import com.gk_dreams.HLuxe.dto.RoomDto;
import com.gk_dreams.HLuxe.entity.Hotel;
import com.gk_dreams.HLuxe.entity.Room;
import com.gk_dreams.HLuxe.entity.User;
import com.gk_dreams.HLuxe.exceptions.ResourceNotFoundException;
import com.gk_dreams.HLuxe.exceptions.UnAuthorisedException;
import com.gk_dreams.HLuxe.repository.HotelRepository;
import com.gk_dreams.HLuxe.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService{

    private final HotelRepository hotelRepository;

    private final ModelMapper modelMapper;

    private final InventoryService inventoryService;

    private final RoomRepository roomRepository;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        LocalDateTime now = LocalDateTime.now();
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        hotel.setActive(false);
        hotel.setCreatedAt(now);
        hotel.setUpdatedAt(now);
        hotel.setOwner(getCurrentUser());
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel Not found with ID"));
        if(!getCurrentUser().equals(hotel.getOwner())){
            throw new UnAuthorisedException("This user does not own hotel with Id: "+ id);
        }
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        LocalDateTime now = LocalDateTime.now();
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel Not found with ID"));

        if(!getCurrentUser().equals(hotel.getOwner())){
            throw new UnAuthorisedException("This user does not own hotel with Id: "+ id);
        }

        modelMapper.map(hotelDto, hotel);
        hotel.setId(id);
        hotel.setCreatedAt(hotel.getCreatedAt());
        hotel.setUpdatedAt(now);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel Not found with ID"));

        if(!getCurrentUser().equals(hotel.getOwner())){
            throw new UnAuthorisedException("This user does not own hotel with Id: "+ id);
        }

        for(Room room : hotel.getRooms()){
            inventoryService.deleteAllInventories(room);
            roomRepository.deleteById(room.getId());
        }
        hotelRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void activateHotel(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel Not found with ID"));

        if(!getCurrentUser().equals(hotel.getOwner())){
            throw new UnAuthorisedException("This user does not own hotel with Id: "+ id);
        }

        hotel.setActive(true);
        for(Room room : hotel.getRooms()){
            inventoryService.initialiseRoomForAYear(room);
        }
    }

    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with Id: "+hotelId));

        List<RoomDto> rooms = hotel.getRooms()
                .stream()
                .map(element -> modelMapper.map(element, RoomDto.class))
                .toList();
        return new HotelInfoDto(modelMapper.map(hotel, HotelDto.class), rooms);
    }

    private User getCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
