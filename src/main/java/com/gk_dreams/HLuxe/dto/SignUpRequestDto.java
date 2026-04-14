package com.gk_dreams.HLuxe.dto;

import com.gk_dreams.HLuxe.enums.Role;
import lombok.Data;

@Data
public class SignUpRequestDto {
    private String email;

    private String password;

    private String name;

    private Role[] roles;
}
