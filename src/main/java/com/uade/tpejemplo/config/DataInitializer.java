package com.uade.tpejemplo.config;

import com.uade.tpejemplo.model.Rol;
import com.uade.tpejemplo.model.Usuario;
import com.uade.tpejemplo.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Inyección por constructor (Buena práctica recomendada por la cátedra)
    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        String adminUsername = "admin";

        if (!usuarioRepository.existsByUsername(adminUsername)) {
            
            Usuario admin = Usuario.builder()
                    .username(adminUsername)
                    .password(passwordEncoder.encode("admin"))
                    .rol(Rol.ADMIN)
                    .puedeAnularCredito(true)  
                    .puedeAnularCobranza(true)
                    .build();

            usuarioRepository.save(admin);
            System.out.println("--> [DataInitializer] Usuario 'admin' creado exitosamente con contraseña 'admin'.");
        } else {
            System.out.println("--> [DataInitializer] El usuario 'admin' ya existe en la base de datos.");
        }
    }
}