package com.psionicgeek.demobankingapp.mappers;

import com.psionicgeek.demobankingapp.entities.Users;
import com.psionicgeek.demobankingapp.models.UserDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

        Users userDtoToUser(UserDTO dto);

        UserDTO userToUserDto(Users user);

}
