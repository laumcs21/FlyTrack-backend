package com.flytrack.Business.Dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservaRequest {

    @NotEmpty (message = "Debe haber al menos un pasajero")
    @Valid
    private List<PasajeroDTO> pasajeros;
    private Long vueloId;
}