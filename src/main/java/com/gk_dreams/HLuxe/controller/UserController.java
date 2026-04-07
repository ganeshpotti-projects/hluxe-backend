package com.gk_dreams.HLuxe.controller;

import com.gk_dreams.HLuxe.entity.User;
import com.gk_dreams.HLuxe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
