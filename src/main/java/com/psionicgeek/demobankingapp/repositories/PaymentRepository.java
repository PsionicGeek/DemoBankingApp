package com.psionicgeek.demobankingapp.repositories;

import com.psionicgeek.demobankingapp.entities.Account;
import com.psionicgeek.demobankingapp.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository  extends JpaRepository<Payment, UUID> {

    List<Payment> findAllByUserId(UUID id);

}
