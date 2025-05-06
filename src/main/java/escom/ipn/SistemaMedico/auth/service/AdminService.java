package escom.ipn.SistemaMedico.auth.service;

import escom.ipn.SistemaMedico.auth.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private UsuarioService usuarioService;

    public boolean register(Usuario usuario) {
        try {
            // Registrar al usuario con el rol "ROLE_ADMIN"
            usuarioService.register(usuario, "ROLE_ADMIN");
            return true;
        } catch (IllegalArgumentException e) {
            // Manejar el caso en que el correo ya esté registrado
            throw new IllegalArgumentException("Error al registrar el administrador: " + e.getMessage());
        }
    }

    public boolean authenticate(String correo, String contrasena) {
        // Delegar la autenticación a UsuarioService
        return usuarioService.authenticate(correo, contrasena);
    }
}