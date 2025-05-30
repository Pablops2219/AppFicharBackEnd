package com.somllar.fichar.service.impl;


import com.somllar.fichar.persistance.entity.*;
import com.somllar.fichar.persistance.repository.CasaRepository;
import com.somllar.fichar.persistance.repository.ManagerCasaRepository;
import com.somllar.fichar.persistance.repository.RolesRepository;
import com.somllar.fichar.persistance.repository.UsuarioRepository;
import com.somllar.fichar.security.controller.dto.AuthCreateUserRequest;
import com.somllar.fichar.security.controller.dto.AuthLoginRequest;
import com.somllar.fichar.security.controller.dto.AuthResponse;
import com.somllar.fichar.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.somllar.fichar.persistance.entity.UsuarioEntity.generarUsername;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private CasaRepository casaRepository;
    @Autowired
    private UsuarioServiceImpl usuarioService;
    @Autowired
    private ManagerCasaRepository managerCasaRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UsuarioEntity userEntity = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con email " + email + " no existe."));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRoles()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        userEntity.getRoles().stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));


        return new User(userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNoExpired(),
                userEntity.isCredentialNoExpired(),
                userEntity.isAccountNoLocked(),
                authorityList);
    }

    public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {

        String email = authLoginRequest.email();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtil.createToken(authentication);
        AuthResponse authResponse = new AuthResponse(email, "User logged in successfully", accessToken, true);

        return authResponse;
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException(String.format("Invalid username or password"));
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect Password");
        }

        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }

    public AuthResponse createUserNoPassword(String email){

        return null;
    }

    public AuthResponse createUser(AuthCreateUserRequest authCreateUserRequest, boolean registerAsUser){
        String firstName = authCreateUserRequest.firstName();
        String lastName = authCreateUserRequest.lastName();
        String password = authCreateUserRequest.password();
        String email = authCreateUserRequest.email();
        List<String> roleRequest = new ArrayList<>();
        Set<RolesEntity> rolesEntitySet = new HashSet<>();

        //Se crea con roles si se crea el usuario desde el endpoint api/usuarios post
        if(!registerAsUser){
            roleRequest = authCreateUserRequest.rolesRequest().roleListName();
            rolesEntitySet = rolesRepository.findByRoleEnumIn(roleRequest).stream().collect(Collectors.toSet());
        } else {
            rolesEntitySet = new HashSet<>(rolesRepository.findByRoleEnumIn(List.of(RoleEnum.USER.name())));
        }


        if(rolesEntitySet.isEmpty() && !registerAsUser){
            throw new IllegalArgumentException("Los roles especificados no existen");
        }

        UsuarioEntity usuarioEntity = UsuarioEntity.builder().
                firstName(firstName).
                lastName(lastName).
                email(email).
                password(passwordEncoder.encode(password)).
                roles(rolesEntitySet).
                accountNoExpired(true).
                accountNoLocked(true).
                isEnabled(true).
                credentialNoExpired(true).
                build();

        usuarioEntity.setUsername(generarUsername(usuarioEntity));

        UsuarioEntity usuarioCreado = usuarioRepository.save(usuarioEntity);

        if (roleRequest.contains("MANAGER") && authCreateUserRequest.casasManager() != null) {
            for (Long casaId : authCreateUserRequest.casasManager()) {
                CasaEntity casa = casaRepository.findById(casaId)
                        .orElseThrow(() -> new RuntimeException("Casa no encontrada"));

                ManagerCasaEntity managerCasa = ManagerCasaEntity.builder()
                        .manager(usuarioCreado)
                        .casa(casa)
                        .build();
                managerCasaRepository.save(managerCasa);
            }
        }

        List<GrantedAuthority> authorityList = new ArrayList<>();

        usuarioCreado.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        usuarioCreado.getRoles().stream().flatMap(role -> role.getPermissionList().stream()).forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(usuarioCreado.getEmail(), usuarioCreado.getPassword(), authorityList);
        String accessToken = jwtUtil.createToken(authentication);

        AuthResponse authResponse = new AuthResponse(usuarioCreado.getUsername(), "Usuario creaod correctamente", accessToken, true);
        return authResponse;

    }
}