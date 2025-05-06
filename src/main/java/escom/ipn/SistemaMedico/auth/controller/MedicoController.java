package escom.ipn.SistemaMedico.auth.controller;

import escom.ipn.SistemaMedico.auth.model.Medico;
import escom.ipn.SistemaMedico.auth.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Mostrar el formulario de registro de médicos
    @GetMapping("/medico/registro")
    public String showMedicoRegisterPage() {
        return "MedicoRegistro"; // Nombre del archivo HTML para el registro
    }

    // Procesar el registro de médicos
    @PostMapping("/medico/registro")
    public String registerMedico(@RequestParam("nombre") String nombre,
                                 @RequestParam("apellidos") String apellidos,
                                 @RequestParam("licencia") String licencia,
                                 @RequestParam("especialidad") String especialidad,
                                 @RequestParam("correo") String correo,
                                 @RequestParam("contrasena") String contrasena,
                                 RedirectAttributes redirectAttributes) {
        try {
            // Validar los datos del formulario
            if (nombre == null || nombre.isEmpty() || apellidos == null || apellidos.isEmpty() ||
                licencia == null || licencia.isEmpty() || especialidad == null || especialidad.isEmpty() ||
                correo == null || correo.isEmpty() || contrasena == null || contrasena.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Todos los campos son obligatorios.");
                return "redirect:/medico/registro";
            }

            // Encriptar la contraseña
            String hashedPassword = passwordEncoder.encode(contrasena);

            // Crear un nuevo objeto Medico
            Medico nuevoMedico = new Medico();
            nuevoMedico.setNombre(nombre + " " + apellidos);
            nuevoMedico.setCorreo(correo);
            nuevoMedico.setContrasena(hashedPassword);
            nuevoMedico.setLicencia(licencia);
            nuevoMedico.setEspecialidad(especialidad);

            // Intentar registrar al médico
            boolean registrado = medicoService.register(nuevoMedico);

            if (registrado) {
                redirectAttributes.addFlashAttribute("mensaje", "Registro exitoso. Por favor, inicia sesión.");
                return "redirect:/medico/inicio";
            } else {
                redirectAttributes.addFlashAttribute("error", "El médico ya existe.");
                return "redirect:/medico/registro";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar el médico: " + e.getMessage());
            return "redirect:/medico/registro";
        }
    }

    // Mostrar el formulario de inicio de sesión de médicos
    @GetMapping("/medico/inicio")
    public String showMedicoLoginPage() {
        return "MedicoInicio"; // Nombre del archivo HTML para el inicio de sesión
    }

    // Procesar el inicio de sesión de médicos
    @PostMapping("/medico/login")
    public String loginMedico(@RequestParam("correo") String correo,
                              @RequestParam("contrasena") String contrasena,
                              RedirectAttributes redirectAttributes) {
        try {
            // Verificar las credenciales
            boolean autenticado = medicoService.authenticate(correo, contrasena);

            if (autenticado) {
                return "redirect:/medico/dashboard"; // Redirigir al dashboard del médico
            } else {
                redirectAttributes.addFlashAttribute("error", "Credenciales incorrectas.");
                return "redirect:/medico/inicio";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al iniciar sesión: " + e.getMessage());
            return "redirect:/medico/inicio";
        }
    }
}