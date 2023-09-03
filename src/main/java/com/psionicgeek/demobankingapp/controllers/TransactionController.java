package com.psionicgeek.demobankingapp.controllers;

import com.psionicgeek.demobankingapp.models.UserDTO;
import com.psionicgeek.demobankingapp.services.AccountServices;
import com.psionicgeek.demobankingapp.services.PaymentServices;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/transact")
public class TransactionController {

    private final AccountServices accountServices;
    private final PaymentServices paymentServices;

    @PostMapping("/deposit")
    public String deposit(@RequestParam("deposit_amount") String depositAmount,
                          @RequestParam("accountId") String accountId,
                          HttpSession session,
                          RedirectAttributes redirectAttributes){
        try {

            if (depositAmount.isEmpty() || accountId.isEmpty() || !depositAmount.matches("[0-9]+") || Double.parseDouble(depositAmount) == 0) {
                redirectAttributes.addFlashAttribute("error", "Please Enter a Valid Amount");
                return "redirect:/app/dashboard";
            }
            UserDTO user = (UserDTO) session.getAttribute("user");
            UUID accountUUID = UUID.fromString(accountId);
            UUID userUUID = user.getId();
            accountServices.deposit(new BigDecimal(depositAmount), accountUUID);
            redirectAttributes.addFlashAttribute("success", "Amount Deposited Successfully");

        }
        catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Something went wrong");
            return "redirect:/app/dashboard";
        }

        return "redirect:/app/dashboard";
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam("transfer_from") String transfer_from,
                           @RequestParam("transfer_to") String transfer_to,
                           @RequestParam("transfer_amount") String transfer_amount,
                           HttpSession httpSession,
                           RedirectAttributes redirectAttributes){
        try {
            if (transfer_from.isEmpty() || transfer_to.isEmpty() || transfer_amount.isEmpty() || !transfer_amount.matches("[0-9]+") || Double.parseDouble(transfer_amount) == 0) {
                redirectAttributes.addFlashAttribute("error", "Please Enter a Valid Amount");
                return "redirect:/app/dashboard";
            }
            if (transfer_from.equals(transfer_to)){
                redirectAttributes.addFlashAttribute("error", "Cannot Transfer to Same Account");
                return "redirect:/app/dashboard";
            }
            accountServices.transfer(new BigDecimal(transfer_amount), UUID.fromString(transfer_from), UUID.fromString(transfer_to));
            redirectAttributes.addFlashAttribute("success", "Amount Transferred Successfully");
            return "redirect:/app/dashboard";

        }
        catch (RuntimeException e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/app/dashboard";
        }
        catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Something went wrong");
            return "redirect:/app/dashboard";
        }
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam("withdrawal_amount") String withdrawAmount,
                           @RequestParam("accountId") String accountId,
                           HttpSession session,
                           RedirectAttributes redirectAttributes){
        try {
            if (withdrawAmount.isEmpty() || accountId.isEmpty() || !withdrawAmount.matches("[0-9]+") || Double.parseDouble(withdrawAmount) == 0) {
                redirectAttributes.addFlashAttribute("error", "Please Enter a Valid Amount");
                return "redirect:/app/dashboard";
            }

            UUID accountUUID = UUID.fromString(accountId);

            accountServices.withdraw(new BigDecimal(withdrawAmount), accountUUID);
            redirectAttributes.addFlashAttribute("success", "Amount Withdrawn Successfully");

        }
        catch (RuntimeException e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/app/dashboard";
        }
        catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Something went wrong");
            return "redirect:/app/dashboard";
        }

        return "redirect:/app/dashboard";
    }

    @PostMapping("/payment")
    public String payment(@RequestParam("payment_amount") String paymentAmount,
                          @RequestParam("accountId") String accountId,
                          @RequestParam("beneficiary") String beneficiary,
                          @RequestParam("account_number") String beneficiaryAccNo,
                          @RequestParam("reference") String reference,
                          HttpSession session,
                          RedirectAttributes redirectAttributes){
    try {
        if (paymentAmount.isEmpty() || accountId.isEmpty() || !paymentAmount.matches("[0-9]+") || Double.parseDouble(paymentAmount) == 0) {
            redirectAttributes.addFlashAttribute("error", "Please Enter a Valid Amount");
            return "redirect:/app/dashboard";
        }
        if (beneficiary.isEmpty() || beneficiaryAccNo.isEmpty() || reference.isEmpty()){
            redirectAttributes.addFlashAttribute("error", "Please Enter a Valid Beneficiary Details");
            return "redirect:/app/dashboard";
        }
        UUID accountUUID = UUID.fromString(accountId);
        paymentServices.createPayment(new BigDecimal(paymentAmount), accountUUID, beneficiary, beneficiaryAccNo, reference);
        redirectAttributes.addFlashAttribute("success", "Payment Successful");

    }
    catch (RuntimeException e){
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("error", e.getMessage());
        return "redirect:/app/dashboard";
    }
    catch (Exception e){
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("error", "Something went wrong");
        return "redirect:/app/dashboard";
    }

        return "redirect:/app/dashboard";
    }

}
