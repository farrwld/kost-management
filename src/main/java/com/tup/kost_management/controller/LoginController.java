package com.tup.kost_management.controller;

import com.tup.kost_management.entity.User;
import com.tup.kost_management.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository; // Suntikkan repository untuk cek ke database

    @GetMapping("/login")
    public String halamanLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String prosesLogin(
            @RequestParam String username, 
            @RequestParam String password, 
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        // Cek aturan ketat untuk akun Admin
        if ("Admin".equals(username) && "Admin123#".equals(password)) {
            session.setAttribute("userRole", "ADMIN");
            session.setAttribute("userName", "Admin");
            return "redirect:/dashboard";
        }
        
        // Cek apakah username terdaftar di database MySQL
        Optional<User> userOpt = userRepository.findByUsername(username);
        
        if (userOpt.isPresent()) {
            User userDariDb = userOpt.get();
            
            // Cek apakah password yang diinput cocok dengan di database
            if (userDariDb.getPassword().equals(password)) {
                session.setAttribute("userRole", userDariDb.getRole());
                session.setAttribute("userName", userDariDb.getUsername()); 
                return "redirect:/dashboard";
            }
        }
        
        // Jika tidak cocok semua
        redirectAttributes.addFlashAttribute("error", "Username atau Password salah!");
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String prosesLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}