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
                                    "/admin/registro", "/medico/registro", "/paciente/registro", "/admin/registro", 
                                    "/admin/inicio", "/admin/login", "/bienvenida").permitAll()
                            .anyRequest().authenticated()
            )
            .formLogin(form -> form
                            .loginPage("/bienvenida")
                            .successHandler((request, response, authentication) -> {
                                String role = authentication.getAuthorities().iterator().next().getAuthority();
                                if (role.equals("ROLE_ADMIN")) {
                                    response.sendRedirect("/admin/dashboard");
                                } else if (role.equals("ROLE_MEDICO")) {
                                    response.sendRedirect("/medico/dashboard");
                                } else if (role.equals("ROLE_PACIENTE")) {
                                    response.sendRedirect("/paciente/dashboard");
                                } else {
                                    response.sendRedirect("/bienvenida");
                                }
                            })
                            .permitAll()
            )
            .logout(logout -> logout
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/bienvenida?logout")
                            .permitAll()
            )
            .csrf(csrf -> csrf.disable());
        return http.build();
    }
}