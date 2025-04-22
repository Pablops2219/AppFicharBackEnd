package com.somllar.fichar;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.somllar.fichar.security.persistance.entity.UsuarioEntity;
import com.somllar.fichar.security.persistance.repository.UsuarioRepository;
import com.somllar.fichar.security.persistance.entity.Permisos;
import com.somllar.fichar.security.persistance.entity.Roles;
import com.somllar.fichar.security.persistance.entity.RoleEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@SpringBootApplication
//@ConfigurationPropertiesScan
public class FicharApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(FicharApplication.class, args);
        FileInputStream refreshToken =
                new FileInputStream("src/main/resources/fichar-aa031-firebase-adminsdk-fbsvc-6f4b5ce917.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .build();

        FirebaseApp.initializeApp(options);


        System.out.println("Server is Running");
        System.out.println("Documentacion en: http://localhost:8080/swagger-ui.html");
    }

    @Bean
    CommandLineRunner init(UsuarioRepository usuarioRepository) {
        return args -> {
            /* Create PERMISSIONS */
            Permisos createPermission = Permisos.builder()
                    .name("CREATE")
                    .build();

            Permisos readPermission = Permisos.builder()
                    .name("READ")
                    .build();

            Permisos updatePermission = Permisos.builder()
                    .name("UPDATE")
                    .build();

            Permisos deletePermission = Permisos.builder()
                    .name("DELETE")
                    .build();

            Permisos refactorPermission = Permisos.builder()
                    .name("REFACTOR")
                    .build();

            /* Create ROLES */
            Roles roleAdmin = Roles.builder()
                    .roleEnum(RoleEnum.ADMIN)
                    .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
                    .build();

            Roles roleUser = Roles.builder()
                    .roleEnum(RoleEnum.USER)
                    .permissionList(Set.of(createPermission, readPermission))
                    .build();

            Roles roleManager = Roles.builder()
                    .roleEnum(RoleEnum.MANAGER)
                    .permissionList(Set.of(readPermission))
                    .build();

            Roles roleDeveloper = Roles.builder()
                    .roleEnum(RoleEnum.DEVELOPER)
                    .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission))
                    .build();

            /* CREATE USERS */
            UsuarioEntity userSantiago = UsuarioEntity.builder()
                    .firstName("Pablo")
                    .lastName("Pernias")
                    .username("pablops")
                    .email("sanper@somllar.org")
                    .password(new BCryptPasswordEncoder().encode("1111"))
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleDeveloper))
                    .build();

            UsuarioEntity userDaniel = UsuarioEntity.builder()
                    .firstName("Daniel")
                    .lastName("Gómez")
                    .username("dangom")
                    .email("dangom@somllar.org")
                    .password(new BCryptPasswordEncoder().encode("1234"))
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleUser))
                    .build();

            UsuarioEntity userAndrea = UsuarioEntity.builder()
                    .firstName("Andrea")
                    .lastName("Ruiz")
                    .username("andrui")
                    .email("andrui@somllar.org")
                    .password(new BCryptPasswordEncoder().encode("1234"))
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleManager))
                    .build();

            UsuarioEntity userAnyi = UsuarioEntity.builder()
                    .firstName("Anyi")
                    .lastName("Martínez")
                    .username("anymar")
                    .email("anymar@somllar.org")
                    .password(new BCryptPasswordEncoder().encode("1234"))
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleDeveloper))
                    .build();


            usuarioRepository.saveAll(List.of(userSantiago, userDaniel, userAndrea, userAnyi));
        };
    }

}