package escom.ipn.SistemaMedico.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/public/**",
                                    "/admin/registro", "/medico/registro", "/paciente/registro").permitAll()
                            .anyRequest().authenticated()
            )
            .formLogin(form -> form
                            .loginPage("/selecRol") // Ruta personalizada para la página de inicio de sesión
                            .permitAll()
            )
            .logout(logout -> logout
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/login?logout")
                            .permitAll()
            )
            .csrf(csrf -> csrf.disable()); // Deshabilitar CSRF si no es necesario (opcional)
        return http.build();
    }
}