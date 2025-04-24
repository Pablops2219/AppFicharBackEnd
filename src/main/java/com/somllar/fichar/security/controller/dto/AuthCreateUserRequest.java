package com.somllar.fichar.security.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record AuthCreateUserRequest (@NotBlank String password,
                                     @NotBlank String email,
                                     @NotBlank String firstName,
                                     @NotBlank String lastName,
                                     @Valid AuthCreateRoleRequest rolesRequest){
}
/**

 {
 "email": "pablops2219@gmail.com",
 "firstName": "Sigma",
 "lastName": "Pernias",
 "password": "1111",
 "rolesRequest": {
 "roleListName":["USER"]
 }
 }


 **/