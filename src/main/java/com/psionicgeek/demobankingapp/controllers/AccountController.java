package com.psionicgeek.demobankingapp.controllers;

import com.psionicgeek.demobankingapp.helpers.GenAccountNumber;
import com.psionicgeek.demobankingapp.models.AccountDTO;
import com.psionicgeek.demobankingapp.models.UserDTO;
import com.psionicgeek.demobankingapp.services.AccountServices;
import com.psionicgeek.demobankingapp.services.UserServices;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountServices accountServices;

    @PostMapping("/create_account")
    public String createAccount(@RequestParam("account_name") String account_name,
                                @RequestParam("account_type") String account_type, HttpSession session,
                                RedirectAttributes redirectAttributes){
       if(account_name.isEmpty() || account_type.isEmpty()) {
           redirectAttributes.addFlashAttribute("error", "Please fill all the fields");
           return "redirect:/app/dashboard";
       }
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if (userDTO == null){
            redirectAttributes.addFlashAttribute("error", "Please Login to Continue");
            return "redirect:/login";
        }
        //System.out.println(userDTO.getId().toString().length());
        AccountDTO accountDTO = AccountDTO.builder()
                .accountName(account_name)
                .accountType(account_type)
                .userId(userDTO.getId())
                .accountNumber(GenAccountNumber.generateAccountNumber()+"")
                .balance(BigDecimal.valueOf(0))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        accountServices.createAccount(accountDTO);
        return "redirect:/app/dashboard";

    }
}
