package escom.ipn.SistemaMedico.auth.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {

    private final Map<String, String> adminDatabase = new HashMap<>(); // Simulación de base de datos

    private final BCryptPasswordEncoder passwordEncoder;

    public AdminService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public boolean authenticate(String usuario, String contrasena) {
        String storedPassword = adminDatabase.get(usuario);
        return storedPassword != null && passwordEncoder.matches(contrasena, storedPassword);
    }

    public boolean register(String usuario, String correo, String hashedPassword) {
        if (adminDatabase.containsKey(usuario)) {
            return false; // El usuario ya existe
        }
        adminDatabase.put(usuario, hashedPassword); // Guardar usuario y contraseña en la "base de datos"
        return true;
    }
}