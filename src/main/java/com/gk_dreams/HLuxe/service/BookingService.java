package com.gk_dreams.HLuxe.service;

import com.gk_dreams.HLuxe.dto.BookingDto;
import com.gk_dreams.HLuxe.dto.BookingRequest;
import com.gk_dreams.HLuxe.dto.GuestDto;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface BookingService {
    BookingDto initialiseBooking(BookingRequest bookingRequest);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestsDtoList);
}
