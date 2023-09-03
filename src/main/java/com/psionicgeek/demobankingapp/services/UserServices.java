package com.psionicgeek.demobankingapp.services;

import com.psionicgeek.demobankingapp.models.UserDTO;
import jakarta.transaction.Transactional;

public interface UserServices {

    @Transactional
    public  void registerUser(UserDTO userDTO);

    UserDTO findByEmail(String email);

    @Transactional
    public void verifyUser(String token, String code);

    @Transactional
    public UserDTO login(String email, String password, String token);
}
