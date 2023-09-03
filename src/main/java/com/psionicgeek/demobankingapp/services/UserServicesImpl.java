package com.psionicgeek.demobankingapp.services;


import com.psionicgeek.demobankingapp.MailSender.MailSender;
import com.psionicgeek.demobankingapp.entities.Users;
import com.psionicgeek.demobankingapp.helpers.HTML;
import com.psionicgeek.demobankingapp.helpers.Token;
import com.psionicgeek.demobankingapp.mappers.UserMapper;
import com.psionicgeek.demobankingapp.models.UserDTO;
import com.psionicgeek.demobankingapp.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@Primary
@RequiredArgsConstructor
public class UserServicesImpl implements UserServices {


    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    @Transactional
    public void registerUser(UserDTO userDTO){

        String token = Token.generateToken();
        Random random = new Random();
        int bound = 1000;
        int code = bound * random.nextInt(bound);

        String emailBody = HTML.htmlEmailTemplate(token,code);
        String hashedPassword = BCrypt.hashpw(userDTO.getPassword(),BCrypt.gensalt(12));
        userDTO.setPassword(hashedPassword);
        System.out.println(hashedPassword);
        userDTO.setToken(token);
        userDTO.setVerified(0);
        userDTO.setCode(code+"");
        userDTO.setCreatedAt(LocalDateTime.now());
        userDTO.setUpdatedAt(LocalDateTime.now());


        userRepository.save(userMapper.userDtoToUser(userDTO));
        MailSender.httpEmailSender("no-reply@demobank.com",userDTO.getEmail(),"Verify your email",emailBody);

    }

    @Override
    public UserDTO findByEmail(String email) {
        Users user = userRepository.findByEmail(email);
        if (user == null){
            return null;
        }
        return userMapper.userToUserDto(user);

    }

    @Override
    @Transactional
    public void verifyUser(String token, String code) {
        Users user = userRepository.findByTokenAndCode(token,code);
        if (user == null){

            throw new RuntimeException("Invalid Token or Code");
        }

            user.setVerified(1);
            user.setVerifiedAt(LocalDate.now());
            user.setToken(null);
            user.setCode(null);
            userRepository.save(user);

    }

    @Override
    @Transactional
    public UserDTO login(String email, String password, String token) {
        Users user = userRepository.findByEmail(email);
        System.out.println(user.getId());
        if (user == null){
            throw new RuntimeException("Invalid Email");
        }
        if (user.getVerified() == 0){

            throw new RuntimeException("Please Verify your Email");
        }
        if (!BCrypt.checkpw(password,user.getPassword())){
            throw new RuntimeException("Invalid Password");
        }
        user.setToken(token);
        userRepository.save(user);
        return userMapper.userToUserDto(user);

    }

}
