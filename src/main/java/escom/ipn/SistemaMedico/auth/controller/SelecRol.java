package escom.ipn.SistemaMedico.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SelecRol {

    @GetMapping("/selecRol")
    public String seleccionarRol() {
        return "SeleccionRol"; // Renderiza la plantilla SeleccionRol.html
    }
}