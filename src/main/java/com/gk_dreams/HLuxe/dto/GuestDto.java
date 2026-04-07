package com.gk_dreams.HLuxe.dto;

import com.gk_dreams.HLuxe.entity.User;
import com.gk_dreams.HLuxe.enums.Gender;
import lombok.Data;

@Data
public class GuestDto {

    private Long id;

    private User user;

    private String name;

    private Gender gender;

    private Integer age;
}
