package com.flytrack.Business.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerificarCodigoRequest {
    private String documento;
    private String codigo;
}
