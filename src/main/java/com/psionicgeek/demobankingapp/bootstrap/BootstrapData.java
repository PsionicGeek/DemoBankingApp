package com.psionicgeek.demobankingapp.bootstrap;


import com.psionicgeek.demobankingapp.entities.Users;
import com.psionicgeek.demobankingapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
   private final UserRepository userRepository;


    @Override
    public void run(String... args) throws Exception {
     //   loadUserData();
    }
    public void loadUserData(){
        Users user1 = Users.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@test.com")
                .password("12345678")
                .code("123456")
                .token("123456")
                .verified(1)
                .createdAt(java.time.LocalDateTime.now())
                .updatedAt(java.time.LocalDateTime.now())
                .verifiedAt(java.time.LocalDate.now())
                .build();
        Users user2 = Users.builder()
                .firstName("John")
                .lastName("Doe 2")
                .email("john.doe2@test.com")
                .password("12345678")
                .code("123456")
                .token("123456")
                .verified(1)
                .createdAt(java.time.LocalDateTime.now())
                .updatedAt(java.time.LocalDateTime.now())
                .verifiedAt(java.time.LocalDate.now())
                .build();
        Users user3 = Users.builder()
                .firstName("John")
                .lastName("Doe 3")
                .email("john.doe2@test.com")
                .password("12345678")
                .code("123456")
                .token("123456")
                .verified(1)
                .createdAt(java.time.LocalDateTime.now())
                .updatedAt(java.time.LocalDateTime.now())
                .verifiedAt(java.time.LocalDate.now())
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

    }
}
