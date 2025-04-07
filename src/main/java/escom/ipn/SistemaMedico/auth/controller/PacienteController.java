package escom.ipn.SistemaMedico.auth.controller;

import escom.ipn.SistemaMedico.auth.model.Usuario;
import escom.ipn.SistemaMedico.auth.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    // Mostrar el formulario de registro de pacientes
    @GetMapping("/paciente/registro")
    public String showPacienteRegisterPage() {
        return "PacienteRegistro"; // Nombre del archivo HTML para el registro
    }

    // Procesar el registro de pacientes
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
        // Crear un nuevo objeto Usuario
        Usuario nuevoPaciente = new Usuario();
        nuevoPaciente.setNombre(nombre + " " + apellidos);
        nuevoPaciente.setCorreo(correo);
        nuevoPaciente.setFechaNacimiento(fechaNacimiento);
        nuevoPaciente.setGenero(genero);
        nuevoPaciente.setDireccion(direccion);
        nuevoPaciente.setTelefono(telefono);
        nuevoPaciente.setContactoEmergencia(contactoEmergencia);
        nuevoPaciente.setTelefonoEmergencia(telefonoEmergencia);
        nuevoPaciente.setEnfermedadesCronicas(enfermedadesCronicas);
        nuevoPaciente.setAlergias(alergias);

        // Intentar registrar al paciente
        boolean registrado = pacienteService.register(nuevoPaciente, "Paciente");

        if (registrado) {
            redirectAttributes.addFlashAttribute("mensaje", "Registro exitoso. Por favor, inicia sesión.");
            return "redirect:/paciente/inicio";
        } else {
            redirectAttributes.addFlashAttribute("error", "El paciente ya existe.");
            return "redirect:/paciente/registro";
        }
    }

    // Mostrar el formulario de inicio de sesión de pacientes
    @GetMapping("/paciente/inicio")
    public String showPacienteLoginPage() {
        return "PacienteInicio"; // Nombre del archivo HTML para el inicio de sesión
    }

    // Procesar el inicio de sesión de pacientes
    @PostMapping("/paciente/login")
    public String loginPaciente(@RequestParam("correo") String correo,
                                @RequestParam("contrasena") String contrasena,
                                RedirectAttributes redirectAttributes) {
        // Verificar las credenciales
        boolean autenticado = pacienteService.authenticate(correo, contrasena);

        if (autenticado) {
            return "redirect:/paciente/dashboard"; // Redirigir al dashboard del paciente
        } else {
            redirectAttributes.addFlashAttribute("error", "Credenciales incorrectas.");
            return "redirect:/paciente/inicio";
        }
    }
}