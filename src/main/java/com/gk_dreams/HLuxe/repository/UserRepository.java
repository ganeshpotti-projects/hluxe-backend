package com.gk_dreams.HLuxe.repository;

import com.gk_dreams.HLuxe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
