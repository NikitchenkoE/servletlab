package com.entity.mapper;

import com.entity.User;
import com.entity.dto.AuthorizedUserDto;

public class MapToAuthorizedUserDto {
    public AuthorizedUserDto mapToAuthorizedUserDto(User user){
        return AuthorizedUserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole().getName())
                .build();
    }
}
