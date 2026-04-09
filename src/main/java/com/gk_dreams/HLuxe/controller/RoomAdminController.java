package com.gk_dreams.HLuxe.controller;

import com.gk_dreams.HLuxe.dto.RoomDto;
import com.gk_dreams.HLuxe.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/hotels/{hotelId}/rooms")
@RequiredArgsConstructor
public class RoomAdminController {

    private final RoomService roomService;

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDto> getRoom(@PathVariable Long hotelId, @PathVariable Long roomId){
        RoomDto roomDto = roomService.getRoom(roomId);
        return new ResponseEntity<>(roomDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RoomDto> createRoom(@PathVariable Long hotelId, @RequestBody RoomDto roomDto){
        RoomDto createdRoom = roomService.createNewRoom(hotelId, roomDto);
        return new ResponseEntity<>(createdRoom, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRoomsInHotel(@PathVariable Long hotelId){
        List<RoomDto> rooms = roomService.getAllRooms(hotelId);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<RoomDto> deleteRoom(@PathVariable Long hotelId, @PathVariable Long roomId){
        roomService.deleteRoom(roomId);
        return ResponseEntity.noContent().build();
    }
}
