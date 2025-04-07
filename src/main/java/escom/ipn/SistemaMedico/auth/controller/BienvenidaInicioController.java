package escom.ipn.SistemaMedico.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BienvenidaInicioController {

    @GetMapping("/bienvenida")
    public String mostrarBienvenida() {
        return "BienvenidaInicio"; // Renderiza la plantilla BienvenidaInicio.html
    }
}