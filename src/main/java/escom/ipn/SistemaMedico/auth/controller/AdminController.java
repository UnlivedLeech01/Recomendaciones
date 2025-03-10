package escom.ipn.SistemaMedico.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

    @GetMapping("/admin/bienvenido")
    public String showAdminWelcomePage() {
        return "AdminBienvenido";
    }

    @GetMapping("/admin/inicio")
    public String showAdminLoginPage() {
        return "AdminInicio";
    }

    @PostMapping("/admin/login")
    public String loginAdmin(@RequestParam("usuario") String usuario, 
                             @RequestParam("contrasena") String contrasena, 
                             RedirectAttributes redirectAttributes) {
        // Aquí puedes agregar la lógica de autenticación
        if ("admin".equals(usuario) && "admin".equals(contrasena)) {
            return "redirect:/admin/bienvenido";
        } else {
            redirectAttributes.addFlashAttribute("error", "Usuario o contraseña incorrectos");
            return "redirect:/admin/inicio";
        }
    }

    @GetMapping("/admin/registro")
    public String showAdminRegisterPage() {
        return "AdminRegistro";
    }

    @PostMapping("/admin/registro")
    public String registerAdmin(@RequestParam("usuario") String usuario, 
                                @RequestParam("correo") String correo, 
                                @RequestParam("contrasena") String contrasena, 
                                RedirectAttributes redirectAttributes) {
        // Aquí puedes agregar la lógica de registro
        // Por ahora, simplemente redirigimos a la página de inicio de sesión
        redirectAttributes.addFlashAttribute("mensaje", "Registro exitoso. Por favor, inicia sesión.");
        return "redirect:/admin/inicio";
    }
}