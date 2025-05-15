package escom.ipn.SistemaMedico.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import escom.ipn.SistemaMedico.auth.model.Usuario;
import escom.ipn.SistemaMedico.auth.service.AdminService;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

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
    try {
        System.out.println("Recibidos los datos para registro: Usuario=" + usuario + ", Correo=" + correo);

        // Validar los datos del formulario
        if (usuario == null || usuario.isEmpty() || correo == null || correo.isEmpty() || contrasena == null || contrasena.isEmpty()) {
            System.out.println("Los datos son incompletos: Usuario=" + usuario + ", Correo=" + correo + ", Contraseña=" + contrasena);
            redirectAttributes.addFlashAttribute("error", "Todos los campos son obligatorios.");
            return "redirect:/admin/registro";
        }

        if (!correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            System.out.println("Correo inválido: " + correo);
            redirectAttributes.addFlashAttribute("error", "El correo electrónico no es válido.");
            return "redirect:/admin/registro";
        }

        // Crear un nuevo objeto Usuario
        Usuario nuevoAdmin = new Usuario();
        nuevoAdmin.setNombre(usuario);
        nuevoAdmin.setCorreo(correo);
        nuevoAdmin.setContrasena(contrasena); // La contraseña será encriptada en el servicio

        System.out.println("Intentando registrar al nuevo admin: " + nuevoAdmin);

        // Intentar registrar al administrador
        adminService.register(nuevoAdmin);

        System.out.println("Registro exitoso para el usuario: " + usuario);

        redirectAttributes.addFlashAttribute("mensaje", "Registro exitoso. Por favor, inicia sesión.");
        return "redirect:/admin/inicio";
    } catch (IllegalArgumentException e) {
        System.out.println("Error de validación: " + e.getMessage());
        redirectAttributes.addFlashAttribute("error", e.getMessage());
        return "redirect:/admin/registro";
    } catch (Exception e) {
        System.out.println("Error al registrar el administrador: " + e.getMessage());
        redirectAttributes.addFlashAttribute("error", "Error al registrar el administrador: " + e.getMessage());
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
    public String loginAdmin(@RequestParam("correo") String correo,
                             @RequestParam("contrasena") String contrasena,
                             RedirectAttributes redirectAttributes) {
        try {
            // Verificar las credenciales
            boolean autenticado = adminService.authenticate(correo, contrasena);

            if (autenticado) {
                return "redirect:/admin/dashboard"; // Redirigir al dashboard del administrador
            } else {
                redirectAttributes.addFlashAttribute("error", "Credenciales incorrectas.");
                return "redirect:/admin/inicio";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al iniciar sesión: " + e.getMessage());
            return "redirect:/admin/inicio";
        }
    }
}