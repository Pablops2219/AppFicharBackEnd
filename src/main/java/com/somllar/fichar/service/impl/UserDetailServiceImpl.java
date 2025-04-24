package com.somllar.fichar.service.impl;


import com.somllar.fichar.persistance.entity.RolesEntity;
import com.somllar.fichar.persistance.entity.UsuarioEntity;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private UsuarioServiceImpl usuarioService;
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

    public AuthResponse createUser(AuthCreateUserRequest authCreateUserRequest){
        String firstName = authCreateUserRequest.firstName();
        String lastName = authCreateUserRequest.lastName();
        String password = authCreateUserRequest.password();
        String email = authCreateUserRequest.email();
        List<String> roleRequest = authCreateUserRequest.rolesRequest().roleListName();

        Set<RolesEntity> rolesEntitySet = rolesRepository.findByRoleEnumIn(roleRequest).stream().collect(Collectors.toSet());
        if(rolesEntitySet.isEmpty()){
            throw new IllegalArgumentException("Los roles especificados no existen");
        }

        UsuarioEntity usuarioEntity = UsuarioEntity.builder().
                firstName(firstName).
                lastName(lastName).
                username(usuarioService.generarUsername(firstName, lastName)).
                email(email).
                password(passwordEncoder.encode(password)).
                roles(rolesEntitySet).
                accountNoExpired(true).
                accountNoLocked(true).
                isEnabled(true).
                credentialNoExpired(true).
                build();

        UsuarioEntity usuarioCreado = usuarioRepository.save(usuarioEntity);

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