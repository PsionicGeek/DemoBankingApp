package com.psionicgeek.demobankingapp.controllers;

import com.psionicgeek.demobankingapp.helpers.Token;
import com.psionicgeek.demobankingapp.models.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BankController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcomePage(){
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(ModelMap modelMap){
        String token= Token.generateToken();
        System.out.println(token);
        modelMap.addAttribute("token", token);

        return "login";
    }
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage(ModelMap modelMap){
        modelMap.addAttribute("registerUser", new UserDTO());

        return "register";
    }


}
