package com.somllar.fichar.dto;

import com.somllar.fichar.persistance.entity.CasaEntity;
import lombok.Data;

import java.util.List;

@Data
public class ManagerDTO {
    private Long id;
    private String firstName;
    private String LastName;
    private List<CasaEntity> casasVigiladas;
}
