package com.somllar.fichar.security.controller;

import com.somllar.fichar.security.controller.dto.AuthCreateUserRequest;
import com.somllar.fichar.security.controller.dto.AuthLoginRequest;
import com.somllar.fichar.security.controller.dto.AuthResponse;
import com.somllar.fichar.service.UsuarioService;
import com.somllar.fichar.service.impl.UserDetailServiceImpl;
import com.somllar.fichar.service.impl.UsuarioServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private UsuarioServiceImpl usuarioService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest userRequest){
        return new ResponseEntity<>(this.userDetailService.createUser(userRequest, true), HttpStatus.CREATED);
    }

    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest){
        return new ResponseEntity<>(this.userDetailService.loginUser(userRequest), HttpStatus.OK);
    }

    @PostMapping("/confirm-user")
    public ResponseEntity<AuthResponse> confirmarUsuario(@Valid @RequestBody AuthLoginRequest userRequest) {
        return new ResponseEntity<>(usuarioService.confirmarUsuario(userRequest), HttpStatus.CREATED);
    }
}