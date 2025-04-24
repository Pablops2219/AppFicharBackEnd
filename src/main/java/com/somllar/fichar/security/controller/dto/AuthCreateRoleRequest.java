package com.somllar.fichar.security.controller.dto;

import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.List;
@Validated
public record AuthCreateRoleRequest(@Size(max = 3, message = "The user must have less than 4 roles") List<String> roleListName) {
}
