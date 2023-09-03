package com.psionicgeek.demobankingapp.repositories;

import com.psionicgeek.demobankingapp.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {




    Users findByEmail(String email);



    Users findByTokenAndCode(String token, String code);
}
