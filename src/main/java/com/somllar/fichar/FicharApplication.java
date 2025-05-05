package com.somllar.fichar;


import com.somllar.fichar.persistance.entity.*;
import com.somllar.fichar.persistance.repository.CasaRepository;
import com.somllar.fichar.persistance.repository.FichajeRepository;
import com.somllar.fichar.persistance.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@SpringBootApplication
//@ConfigurationPropertiesScan
public class FicharApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(FicharApplication.class, args);

//        Configuracion de FireBase (en desuso)
//        FileInputStream refreshToken =
//                new FileInputStream("src/main/resources/fichar-aa031-firebase-adminsdk-fbsvc-6f4b5ce917.json");
//
//        FirebaseOptions options = FirebaseOptions.builder()
//                .setCredentials(GoogleCredentials.fromStream(refreshToken))
//                .build();
//
//        FirebaseApp.initializeApp(options);


        System.out.println("Server is Running");
        System.out.println("Documentacion en: http://localhost:8080/swagger-ui.html");
        //System.out.println("Si hay problemas con LomBok ir a: \nAjustes->Build,Execution,Deployment->Compiler->Annotation Processors->Fichar->Elegir manualmente el jar de LomBok");
    }

    @Bean
    CommandLineRunner init(UsuarioRepository usuarioRepository,
                           CasaRepository casaRepository,
                           FichajeRepository fichajeRepository) {
        return args -> {
            /* Create PERMISSIONS */
            PermisosEntity createPermission = PermisosEntity.builder()
                    .name("CREATE")
                    .build();

            PermisosEntity readPermission = PermisosEntity.builder()
                    .name("READ")
                    .build();

            PermisosEntity updatePermission = PermisosEntity.builder()
                    .name("UPDATE")
                    .build();

            PermisosEntity deletePermission = PermisosEntity.builder()
                    .name("DELETE")
                    .build();

            PermisosEntity refactorPermission = PermisosEntity.builder()
                    .name("REFACTOR")
                    .build();

            /* Create ROLES */
            RolesEntity roleUser = RolesEntity.builder()
                    .roleEnum(RoleEnum.USER)
                    .permissionList(Set.of(createPermission, readPermission))
                    .build();


            RolesEntity roleManager = RolesEntity.builder()
                    .roleEnum(RoleEnum.MANAGER)
                    .permissionList(Set.of(createPermission, readPermission))
                    .build();

            RolesEntity roleAdmin = RolesEntity.builder()
                    .roleEnum(RoleEnum.ADMIN)
                    .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
                    .build();


            RolesEntity roleDeveloper = RolesEntity.builder()
                    .roleEnum(RoleEnum.DEVELOPER)
                    .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission))
                    .build();
//
//            /* CREATE USERS */
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
                    .roles(Set.of(roleDeveloper,roleAdmin))
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
                    .roles(Set.of(roleAdmin))
                    .build();


            usuarioRepository.saveAll(List.of(userAnyi, userAndrea, userDaniel, userSantiago));

            CasaEntity casaA = CasaEntity.builder()
                    .nombre("Casa A")
                    .build();

            CasaEntity casaB = CasaEntity.builder()
                    .nombre("Casa B")
                    .build();

            CasaEntity casaC = CasaEntity.builder()
                    .nombre("Casa C")
                    .build();

            casaRepository.saveAll(List.of(casaA, casaB, casaC));

            FichajeEntity fichaje1 = FichajeEntity.builder()
                    .casa(casaA)
                    .hora(LocalDateTime.now())
                    .tipo(FichajeEnum.ENTRADA)
                    .usuario(userSantiago)
                    .build();

            FichajeEntity fichaje2 = FichajeEntity.builder()
                    .casa(casaB)
                    .hora(LocalDateTime.now())
                    .tipo(FichajeEnum.SALIDA)
                    .usuario(userSantiago)
                    .build();

            fichajeRepository.saveAll(List.of(fichaje1, fichaje2));
        };
    }

}