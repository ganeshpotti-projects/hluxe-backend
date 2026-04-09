package com.gk_dreams.HLuxe.service;

import com.gk_dreams.HLuxe.dto.BookingDto;
import com.gk_dreams.HLuxe.dto.BookingRequest;
import com.gk_dreams.HLuxe.dto.GuestDto;
import com.gk_dreams.HLuxe.entity.*;
import com.gk_dreams.HLuxe.enums.BookingStatus;
import com.gk_dreams.HLuxe.exceptions.ResourceNotFoundException;
import com.gk_dreams.HLuxe.exceptions.UnAuthorisedException;
import com.gk_dreams.HLuxe.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{

    private final BookingRepository bookingRepository;

    private final HotelRepository hotelRepository;

    private final RoomRepository roomRepository;

    private final InventoryRepository inventoryRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;
    private final GuestRepository guestRepository;

    @Override
    @Transactional
    public BookingDto initialiseBooking(BookingRequest bookingRequest) {
        Hotel hotel = hotelRepository
                .findById(bookingRequest.getHotelId())
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with Id: "+ bookingRequest.getHotelId()));

        Room room = roomRepository.findById(bookingRequest.getRoomId())
                .orElseThrow(()-> new ResourceNotFoundException("Room not found with Id: "+bookingRequest.getRoomId()));

        List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventory(
                room.getId(),
                bookingRequest.getCheckInDate(),
                bookingRequest.getCheckOutDate(),
                bookingRequest.getRoomCount()
        );

        long daysCount = ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate())+1;
        if(inventoryList.size() != daysCount){
            throw new IllegalStateException("Room is not available anymore.");
        }

        for(Inventory inventory : inventoryList){
            inventory.setReservedCount(inventory.getBookedCount() + bookingRequest.getRoomCount());
        }

        inventoryRepository.saveAll(inventoryList);

//        todo: calculate dynamic price
        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequest.getCheckInDate())
                .checkoutDate(bookingRequest.getCheckOutDate())
                .user(getCurrentUser())
                .roomsCount(bookingRequest.getRoomCount())
                .amount(BigDecimal.TEN)
                .build();
        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }

    @Override
    @Transactional
    public BookingDto addGuests(Long bookingId, List<GuestDto> guestsDtoList) {
        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(()-> new ResourceNotFoundException("Booking not found with Id: "+ bookingId));

        User user = getCurrentUser();

        if(!user.equals(booking.getUser())){
            throw new UnAuthorisedException("Booking does not belong to user with id: "+user.getId());
        }

        if(hasBookingExpired(booking))
            throw new IllegalStateException("Booking has already Expired");

        if(booking.getBookingStatus() != BookingStatus.RESERVED)
            throw new IllegalStateException("Booking is not reserved");

        for(GuestDto guestDto : guestsDtoList){
            Guest guest = modelMapper.map(guestDto, Guest.class);
            guest.setUser(getCurrentUser());
            guest = guestRepository.save(guest);
            booking.getGuests().add(guest);
        }
        booking.setBookingStatus(BookingStatus.GUESTS_ADDED);
        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }

    public boolean hasBookingExpired(Booking booking){
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    public User getCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
