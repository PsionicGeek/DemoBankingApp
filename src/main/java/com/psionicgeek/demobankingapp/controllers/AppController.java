package com.psionicgeek.demobankingapp.controllers;

import com.psionicgeek.demobankingapp.models.AccountDTO;
import com.psionicgeek.demobankingapp.models.PaymentDTO;
import com.psionicgeek.demobankingapp.models.TransactionDTO;
import com.psionicgeek.demobankingapp.models.UserDTO;
import com.psionicgeek.demobankingapp.services.AccountServices;
import com.psionicgeek.demobankingapp.services.PaymentServices;
import com.psionicgeek.demobankingapp.services.TransactionServices;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/app")
public class AppController {

    private final AccountServices accountServices;
    private final PaymentServices paymentServices;
    private final TransactionServices transactionServices;
    @GetMapping("/dashboard")
    public ModelAndView dashboard(HttpSession session, ModelAndView modelAndView){

        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        System.out.println(userDTO);
        List<AccountDTO> accountDTOList = accountServices.getAccountsList(userDTO.getId());
        BigDecimal totalBalance = accountServices.getTotalBalance(userDTO.getId());
        modelAndView.addObject("totalBalance", totalBalance);
        modelAndView.addObject("userAccounts", accountDTOList);
        modelAndView.addObject("user", userDTO);
        modelAndView.setViewName("dashboard");
        return modelAndView;
    }

    @GetMapping("payment_history")
    public ModelAndView paymentHistory(HttpSession session, ModelAndView modelAndView){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        List<PaymentDTO> paymentDTOS= paymentServices.getAllPayments(userDTO.getId());
        modelAndView.addObject("user", userDTO);
        System.out.println(paymentDTOS);
        modelAndView.addObject("payment_history", paymentDTOS);
        modelAndView.setViewName("paymentHistory");
        return modelAndView;
    }

    @GetMapping("transact_history")
    public ModelAndView transactionHistory(HttpSession session, ModelAndView modelAndView){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        List<TransactionDTO> transactionDTOS= transactionServices.getAllTransactions(userDTO.getId());
        modelAndView.addObject("user", userDTO);
        System.out.println(transactionDTOS);
        modelAndView.addObject("transact_history", transactionDTOS);
        modelAndView.setViewName("transactHistory");
        return modelAndView;
    }



}
