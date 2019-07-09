package com.comarch.tomasz.kosacki.mapper;

import com.comarch.tomasz.kosacki.dto.UserDto;
import com.comarch.tomasz.kosacki.userEntity.UserEntity;
import org.modelmapper.ModelMapper;

public class Mapper {

    public UserDto userEntityToUserDto(UserEntity from) {

        ModelMapper modelMapper = new ModelMapper();
        UserDto user = modelMapper.map(from, UserDto.class);

        return user;
    }

    public UserEntity userDtoToUserEntity(UserDto from) {

        ModelMapper modelMapper = new ModelMapper();
        UserEntity user = modelMapper.map(from, UserEntity.class);

        return user;
    }
}
