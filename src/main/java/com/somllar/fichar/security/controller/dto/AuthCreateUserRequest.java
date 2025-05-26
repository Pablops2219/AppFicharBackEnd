package com.somllar.fichar.security.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record AuthCreateUserRequest (@NotBlank String password,
                                     @NotBlank String email,
                                     @NotBlank String firstName,
                                     @NotBlank String lastName,
                                     @Valid AuthCreateRoleRequest rolesRequest,

                                     List<Long> casasManager // IDs de las casas si es manager
){
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



{
  "email": "manager@gmail.com",
          "firstName": "Manager",
          "lastName": "DeCasa",
          "password": "1234",
          "rolesRequest": {
    "roleListName": ["MANAGER"]
},
        "casasManager": [1, 2, 3]
}

 **/