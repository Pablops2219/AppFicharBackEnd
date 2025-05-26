package com.somllar.fichar;


import com.somllar.fichar.persistance.entity.*;
import com.somllar.fichar.persistance.repository.CasaRepository;
import com.somllar.fichar.persistance.repository.FichajeRepository;
import com.somllar.fichar.persistance.repository.RolesRepository;
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
        System.out.println("Server is Running");
        System.out.println("Documentacion en: http://localhost:8080/swagger-ui.html");
        //System.out.println("Si hay problemas con LomBok ir a: \nAjustes->Build,Execution,Deployment->Compiler->Annotation Processors->Fichar->Elegir manualmente el jar de LomBok");
    }

    @Bean
    CommandLineRunner init(UsuarioRepository usuarioRepository,
                           CasaRepository casaRepository,
                           FichajeRepository fichajeRepository,
                           RolesRepository rolesRepository) {

        if (usuarioRepository.count() == 0) {
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


                /* CREATE USERS */
                UsuarioEntity userTest = UsuarioEntity.builder()
                        .firstName("Test")
                        .lastName("Tests")
                        .username("test")
                        .email("test@somllar.org")
                        .password(new BCryptPasswordEncoder().encode("1111"))
                        .isEnabled(true)
                        .accountNoExpired(true)
                        .accountNoLocked(true)
                        .credentialNoExpired(true)
                        .roles(Set.of(roleUser, roleManager, roleAdmin, roleDeveloper))
                        .build();


                usuarioRepository.saveAll(List.of(userTest));

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
                        .usuario(userTest)
                        .build();

                FichajeEntity fichaje2 = FichajeEntity.builder()
                        .casa(casaB)
                        .hora(LocalDateTime.now())
                        .tipo(FichajeEnum.SALIDA)
                        .usuario(userTest)
                        .build();

                fichajeRepository.saveAll(List.of(fichaje1, fichaje2));
            };
        } else {
            return args -> {
            };
        }

    }


}