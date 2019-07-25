package com.comarch.tomasz.kosacki.mapper;

import com.comarch.tomasz.kosacki.dto.UserDto;
import com.comarch.tomasz.kosacki.userEntity.UserEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Mapper {

    public Mapper() {
    }

    public UserDto userEntityToUserDto(UserEntity from) {

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(from, UserDto.class);
    }

    public UserEntity userDtoToUserEntity(UserDto from) {

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(from, UserEntity.class);
    }

    public List<UserDto> userEntityListToUserDtoList(List<UserEntity> userEntityList) {

        ModelMapper modelMapper = new ModelMapper();
        Type listType = new TypeToken<List<UserDto>>() {}.getType();
        return modelMapper.map(userEntityList, listType);
    }
}
