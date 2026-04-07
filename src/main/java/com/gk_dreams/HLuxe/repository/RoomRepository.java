package com.gk_dreams.HLuxe.repository;

import com.gk_dreams.HLuxe.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
