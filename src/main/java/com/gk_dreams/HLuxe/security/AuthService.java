package com.gk_dreams.HLuxe.security;

import com.gk_dreams.HLuxe.dto.LoginDto;
import com.gk_dreams.HLuxe.dto.SignUpRequestDto;
import com.gk_dreams.HLuxe.dto.UserDto;
import com.gk_dreams.HLuxe.entity.User;
import com.gk_dreams.HLuxe.enums.Role;
import com.gk_dreams.HLuxe.exceptions.ResourceNotFoundException;
import com.gk_dreams.HLuxe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    public UserDto signUp(SignUpRequestDto signUpRequestDto){
        User user = userRepository.findByEmail(signUpRequestDto.getEmail()).orElse(null);
        if(user!=null)
            throw new RuntimeException("User already exists with email: "+signUpRequestDto.getEmail());
        User newUser = modelMapper.map(signUpRequestDto, User.class);
        newUser.setRoles(Set.of(Role.GUEST));
        newUser.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        newUser = userRepository.save(newUser);
        return modelMapper.map(newUser, UserDto.class);
    }

    public String[] login(LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()
        ));
        User user = (User) authentication.getPrincipal();
        String[] tokens = new String[2];
        tokens[0] = jwtService.generateAccessToken(user);
        tokens[1] = jwtService.generateRefreshToken(user);
        return tokens;
    }

    public String refreshToken(String refreshToken){
        Long id = jwtService.getUserIdFromToken(refreshToken);
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with Id: "+id));
        return jwtService.generateAccessToken(user);
    }
}
