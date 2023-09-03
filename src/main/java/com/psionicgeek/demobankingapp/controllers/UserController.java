package com.psionicgeek.demobankingapp.controllers;

import com.psionicgeek.demobankingapp.models.UserDTO;
import com.psionicgeek.demobankingapp.services.UserServices;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {




    private final UserServices userServices;


    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute("registerUser") UserDTO userDTO, BindingResult result, @RequestParam("confirm_password") String confirm_password, ModelAndView modelAndView) {
        System.out.println(confirm_password);
        System.out.println(userDTO.getPassword());
        if(result.hasErrors()){

            return modelAndView;
        }
        if (!userDTO.getPassword().equals(confirm_password)){
            result.rejectValue("password", "error.user", "Password and Confirm Password must be same");
            return modelAndView;
        }
         if (userServices.findByEmail(userDTO.getEmail()) != null) {

             result.rejectValue("email", "error.user", "Email already exists");
             return modelAndView;
         }
         try {

             userServices.registerUser(userDTO);
             String successMessage = "Account Registered Successfully, Please Check your Email and Verify Account!";
             modelAndView.addObject("registerUser", new UserDTO());
             modelAndView.addObject("success", successMessage);
         }
         catch (Exception e){
             System.out.println(e.getMessage());
             modelAndView.addObject("error", "Something went wrong, Please try again later");
         }


        return modelAndView;
    }

    @GetMapping("/verify")
    public ModelAndView verify(@RequestParam("token") String token, @RequestParam("code") String code, ModelAndView modelAndView){
        try {
            userServices.verifyUser(token,code);
            modelAndView.addObject("success", "Account Verified Successfully");
            modelAndView.setViewName("login");
            return modelAndView;
        }
        catch (Exception e){
            modelAndView.addObject("error", e.getMessage());
            modelAndView.setViewName("error");
            return modelAndView;
        }
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("_token") String token, ModelAndView modelAndView, HttpSession session){

        if (email.isEmpty() || password.isEmpty()){
            modelAndView.addObject("error", "Email or Password cannot be empty");
            modelAndView.setViewName("login");
            return modelAndView;
        }
        try {
          UserDTO userDTO=  userServices.login(email,password,token);
            session.setAttribute("user", userDTO);
            session.setAttribute("token", token);
            session.setAttribute("authenticated", true);
            System.out.println(userDTO);
            modelAndView.setViewName("redirect:/app/dashboard");
        }
        catch (Exception e){

            modelAndView.addObject("error", e.getMessage());
            modelAndView.setViewName("login");
            return modelAndView;
        }


        return modelAndView;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes){
        session.invalidate();
        redirectAttributes.addFlashAttribute("logged_out", "Logged out Successfully");
        return "redirect:/login";
    }
}
