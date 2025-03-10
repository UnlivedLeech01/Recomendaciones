package escom.ipn.SistemaMedico.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MedicoController {

    @GetMapping("/medico/bienvenido")
    public String showMedicoWelcomePage() {
        return "MedicoBienvenido";
    }

    @GetMapping("/medico/inicio")
    public String showMedicoLoginPage() {
        return "MedicoInicio";
    }

    @PostMapping("/medico/login")
    public String loginMedico(@RequestParam("usuario") String usuario, 
                              @RequestParam("contrasena") String contrasena, 
                              RedirectAttributes redirectAttributes) {
        // Aquí puedes agregar la lógica de autenticación
        if ("medico".equals(usuario) && "medico".equals(contrasena)) {
            return "redirect:/medico/bienvenido";
        } else {
            redirectAttributes.addFlashAttribute("error", "Usuario o contraseña incorrectos");
            return "redirect:/medico/inicio";
        }
    }

    @GetMapping("/medico/registro")
    public String showMedicoRegisterPage() {
        return "MedicoRegistro";
    }

    @PostMapping("/medico/registro")
    public String registerMedico(@RequestParam("nombre") String nombre, 
                                 @RequestParam("apellidos") String apellidos, 
                                 @RequestParam("licencia") String licencia, 
                                 @RequestParam("especialidad") String especialidad, 
                                 @RequestParam("correo") String correo, 
                                 @RequestParam("contrasena") String contrasena, 
                                 RedirectAttributes redirectAttributes) {
        // Aquí puedes agregar la lógica de registro
        // Por ahora, simplemente redirigimos a la página de inicio de sesión
        redirectAttributes.addFlashAttribute("mensaje", "Registro exitoso. Por favor, inicia sesión.");
        return "redirect:/medico/inicio";
    }
}