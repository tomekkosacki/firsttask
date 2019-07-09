package com.comarch.tomasz.kosacki.mapper;

import com.comarch.tomasz.kosacki.dto.UserDto;
import com.comarch.tomasz.kosacki.userEntity.UserEntity;

public class Mapper {

    public UserDto userEntityToUserDto(UserEntity from) {

        UserDto user = new UserDto();
        user.setId(from.getId());
        user.setFirstName(from.getFirstName());
        user.setLastName(from.getLastName());
        user.setEmail(from.getEmail());

        //zly przekazywany typ
        //user.setCreationDate(from.getCreationDate());

        return user;
    }

    public UserEntity userDtoToUserEntity(UserDto from) {

        UserEntity user = new UserEntity();
        user.setId(from.getId());
        user.setFirstName(from.getFirstName());
        user.setLastName(from.getLastName());
        user.setEmail(from.getEmail());

        //zly przekazywany typ
        //user.setCreationDate(from.getCreationDate());

        return user;
    }
}
