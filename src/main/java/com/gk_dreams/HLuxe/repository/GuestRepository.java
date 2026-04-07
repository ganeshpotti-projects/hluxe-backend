package com.gk_dreams.HLuxe.repository;

import com.gk_dreams.HLuxe.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}