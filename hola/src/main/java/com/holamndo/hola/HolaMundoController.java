package com.holamndo.hola;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HolaMundoController {
    
    @GetMapping("/hola")
    public String decirHola() {

        return "Hola Spring";
    }

}
