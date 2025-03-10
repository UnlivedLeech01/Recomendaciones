package escom.ipn.SistemaMedico.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PacienteController {

    @GetMapping("/paciente/bienvenido")
    public String showPacienteWelcomePage() {
        return "PacienteBienvenido";
    }

    @GetMapping("/paciente/inicio")
    public String showPacienteLoginPage() {
        return "PacienteInicio";
    }

    @PostMapping("/paciente/login")
    public String loginPaciente(@RequestParam("usuario") String usuario, 
                                @RequestParam("contrasena") String contrasena, 
                                RedirectAttributes redirectAttributes) {
        // Aquí puedes agregar la lógica de autenticación
        if ("paciente".equals(usuario) && "paciente".equals(contrasena)) {
            return "redirect:/paciente/bienvenido";
        } else {
            redirectAttributes.addFlashAttribute("error", "Usuario o contraseña incorrectos");
            return "redirect:/paciente/inicio";
        }
    }

    @GetMapping("/paciente/registro")
    public String showPacienteRegisterPage() {
        return "PacienteRegistro";
    }

    @PostMapping("/paciente/registro")
    public String registerPaciente(@RequestParam("nombre") String nombre, 
                                   @RequestParam("apellidos") String apellidos, 
                                   @RequestParam("fechaNacimiento") String fechaNacimiento, 
                                   @RequestParam("genero") String genero, 
                                   @RequestParam("direccion") String direccion, 
                                   @RequestParam("telefono") String telefono, 
                                   @RequestParam("correo") String correo, 
                                   @RequestParam("contactoEmergencia") String contactoEmergencia, 
                                   @RequestParam("telefonoEmergencia") String telefonoEmergencia, 
                                   @RequestParam("enfermedadesCronicas") String enfermedadesCronicas, 
                                   @RequestParam("alergias") String alergias, 
                                   RedirectAttributes redirectAttributes) {
        // Aquí puedes agregar la lógica de registro
        // Por ahora, simplemente redirigimos a la página de inicio de sesión
        redirectAttributes.addFlashAttribute("mensaje", "Registro exitoso. Por favor, inicia sesión.");
        return "redirect:/paciente/inicio";
    }
}