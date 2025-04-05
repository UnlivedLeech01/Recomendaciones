package escom.ipn.SistemaMedico.auth.controller;

import escom.ipn.SistemaMedico.auth.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
        // Lógica de autenticación
        if (adminService.authenticate(usuario, contrasena)) {
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
        // Encriptar la contraseña
        String hashedPassword = passwordEncoder.encode(contrasena);

        // Lógica de registro
        if (adminService.register(usuario, correo, hashedPassword)) {
            redirectAttributes.addFlashAttribute("mensaje", "Registro exitoso. Por favor, inicia sesión.");
            return "redirect:/admin/inicio";
        } else {
            redirectAttributes.addFlashAttribute("error", "El usuario ya existe.");
            return "redirect:/admin/registro";
        }
    }
}