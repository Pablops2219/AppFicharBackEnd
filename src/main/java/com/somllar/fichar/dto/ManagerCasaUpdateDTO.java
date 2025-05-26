package com.somllar.fichar.dto;

import lombok.Data;

import java.util.List;

@Data
public class ManagerCasaUpdateDTO {
    private Long managerId;
    private List<Long> casaIds;
}
