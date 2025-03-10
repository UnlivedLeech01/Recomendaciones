package escom.ipn.SistemaMedico.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegisterController {

    @GetMapping("/registro")
    public String showRegisterPage() {
        return "Registro";
    }

    @PostMapping("/registro")
    public ModelAndView handleRegister(@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password) {
        // Aquí puedes agregar la lógica para manejar el registro de usuarios
        // Por ahora, simplemente redirigimos a la página de inicio de sesión
        return new ModelAndView("redirect:/login");
    }
}