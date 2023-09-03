package com.psionicgeek.demobankingapp.mappers;

import com.psionicgeek.demobankingapp.entities.Payment;
import com.psionicgeek.demobankingapp.models.PaymentDTO;
import org.mapstruct.Mapper;

@Mapper
public interface PaymentMapper {
     Payment paymentDtoToPayment(PaymentDTO paymentDTO);
     PaymentDTO paymentToPaymentDto(Payment payment);
}
