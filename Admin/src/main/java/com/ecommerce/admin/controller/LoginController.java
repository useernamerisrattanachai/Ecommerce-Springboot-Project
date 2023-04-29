package com.ecommerce.admin.controller;

import com.ecommerce.library.dto.AdminDto;
import com.ecommerce.library.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ecommerce.library.service.impl.AdminServiceImpl;
import javax.validation.Valid;

@Controller
public class LoginController {
    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("adminDto",new AdminDto());
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model){
        return "forgot-password";
    }

    @PostMapping("/register-new")
    public String addNewAdmin(@Valid  @ModelAttribute("adminDto")AdminDto adminDto, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("adminDto", adminDto);
                result.toString();
                return "register";
            }
            String username = adminDto.getUsername();
            Admin admin = adminService.findByUsername(username);
            if (admin != null){
                model.addAttribute("adminDto",adminDto);
                redirectAttributes.addFlashAttribute("message","Your email has been registered!");
                System.out.println("admin not null");
                return "register";
            }
            if (adminDto.getPassword().equals(adminDto.getRepeatPassword())){
                adminService.save(adminDto);
                System.out.println("success");
                model.addAttribute("adminDto",adminDto);
                redirectAttributes.addFlashAttribute("message","Register successfully!");
            }else {
                model.addAttribute("adminDto",adminDto);
                redirectAttributes.addFlashAttribute("message","Password is not same.");
                System.out.println("Password is not same.");
                return "register";
            }


        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("message","Can't register because error server!");
        }
        return "register";


    }
}
