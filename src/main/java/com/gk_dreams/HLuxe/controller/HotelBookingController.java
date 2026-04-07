package com.gk_dreams.HLuxe.controller;

import com.gk_dreams.HLuxe.dto.BookingDto;
import com.gk_dreams.HLuxe.dto.BookingRequest;
import com.gk_dreams.HLuxe.dto.GuestDto;
import com.gk_dreams.HLuxe.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class HotelBookingController {

    private final BookingService bookingService;

    @PostMapping("/init")
    public ResponseEntity<BookingDto> initialiseBooking(@RequestBody BookingRequest bookingRequest){
        return ResponseEntity.ok(bookingService.initialiseBooking(bookingRequest));
    }

    @PostMapping("/{bookingId}/addGuests")
    public ResponseEntity<BookingDto> addGuests(@PathVariable Long bookingId, @RequestBody List<GuestDto> guestsDtoList){
        return ResponseEntity.ok(bookingService.addGuests(bookingId, guestsDtoList));
    }
}
