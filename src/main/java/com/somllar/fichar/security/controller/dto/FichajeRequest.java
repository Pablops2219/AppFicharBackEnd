package com.somllar.fichar.security.controller.dto;

import com.somllar.fichar.persistance.entity.FichajeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FichajeRequest {
    private Long casaid;
    private FichajeEnum tipo;
}
