package escom.ipn.SistemaMedico.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "Inicio";
    }

    @PostMapping("/login")
    public ModelAndView handleLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        if ("admin".equals(username) && "admin".equals(password)) {
            return new ModelAndView("redirect:/bienvenido");
        } else {
            return new ModelAndView("redirect:/error");
        }
    }
}