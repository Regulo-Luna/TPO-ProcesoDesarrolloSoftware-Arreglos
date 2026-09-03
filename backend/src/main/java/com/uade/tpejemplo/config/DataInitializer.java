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
        
        // 1. Creación del Admin
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
            System.out.println("--> [DataInitializer] Usuario 'admin' creado exitosamente.");
        } else {
            System.out.println("--> [DataInitializer] El usuario 'admin' ya existe.");
        }

        // 2. Creación del Supervisor
        String supervisorUsername = "supervisor";
        if (!usuarioRepository.existsByUsername(supervisorUsername)) {
            Usuario supervisor = Usuario.builder()
                    .username(supervisorUsername)
                    .password(passwordEncoder.encode("supervisor"))
                    .rol(Rol.SUPERVISOR) // IMPORTANTE: Asegúrate de tener SUPERVISOR en tu enum Rol
                    .puedeAnularCredito(true) // Ajusta estos permisos según tu regla de negocio
                    .puedeAnularCobranza(true)
                    .build();

            usuarioRepository.save(supervisor);
            System.out.println("--> [DataInitializer] Usuario 'supervisor' creado exitosamente.");
        } else {
            System.out.println("--> [DataInitializer] El usuario 'supervisor' ya existe.");
        }

        // 3. Creación del Usuario normal
        String userUsername = "user";
        if (!usuarioRepository.existsByUsername(userUsername)) {
            Usuario normalUser = Usuario.builder()
                    .username(userUsername)
                    .password(passwordEncoder.encode("user"))
                    .rol(Rol.USER) 
                    .puedeAnularCredito(false) 
                    .puedeAnularCobranza(false)
                    .build();

            usuarioRepository.save(normalUser);
            System.out.println("--> [DataInitializer] Usuario 'user' creado exitosamente.");
        } else {
            System.out.println("--> [DataInitializer] El usuario 'user' ya existe.");
        }
    }
}