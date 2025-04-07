package escom.ipn.SistemaMedico.auth.controller;

import escom.ipn.SistemaMedico.auth.model.Usuario;
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

    // Mostrar el formulario de registro
    @GetMapping("/admin/registro")
    public String showAdminRegisterPage() {
        return "AdminRegistro"; // Nombre del archivo HTML para el registro
    }

    // Procesar el registro
    @PostMapping("/admin/registro")
    public String registerAdmin(@RequestParam("usuario") String usuario,
                                @RequestParam("correo") String correo,
                                @RequestParam("contrasena") String contrasena,
                                RedirectAttributes redirectAttributes) {
        // Encriptar la contraseña
        String hashedPassword = passwordEncoder.encode(contrasena);

        // Crear un nuevo objeto Usuario
        Usuario nuevoAdmin = new Usuario();
        nuevoAdmin.setNombre(usuario);
        nuevoAdmin.setCorreo(correo);
        nuevoAdmin.setContrasena(hashedPassword);

        // Intentar registrar al administrador
        boolean registrado = adminService.register(nuevoAdmin, "Administrador");

        if (registrado) {
            redirectAttributes.addFlashAttribute("mensaje", "Registro exitoso. Por favor, inicia sesión.");
            return "redirect:/admin/inicio";
        } else {
            redirectAttributes.addFlashAttribute("error", "El usuario ya existe.");
            return "redirect:/admin/registro";
        }
    }

    // Mostrar el formulario de inicio de sesión
    @GetMapping("/admin/inicio")
    public String showAdminLoginPage() {
        return "AdminInicio"; // Nombre del archivo HTML para el inicio de sesión
    }

    // Procesar el inicio de sesión
    @PostMapping("/admin/login")
    public String loginAdmin(@RequestParam("usuario") String usuario,
                             @RequestParam("contrasena") String contrasena,
                             RedirectAttributes redirectAttributes) {
        // Verificar las credenciales
        boolean autenticado = adminService.authenticate(usuario, contrasena);

        if (autenticado) {
            return "redirect:/admin/dashboard"; // Redirigir al dashboard del administrador
        } else {
            redirectAttributes.addFlashAttribute("error", "Credenciales incorrectas.");
            return "redirect:/admin/inicio";
        }
    }
}