package com.flytrack.Persistence.Mapper;

import com.flytrack.Business.Dto.VueloResponse;
import com.flytrack.Persistence.Model.Vuelo;

public class VueloMapper {

    public static VueloResponse toDTO(Vuelo vuelo) {

        if (vuelo == null) {
            return null;
        }

        return VueloResponse.builder()
                .id(vuelo.getId())
                .numeroVuelo(vuelo.getNumeroVuelo())
                .aerolinea(vuelo.getAerolinea())
                .origen(vuelo.getOrigen())
                .destino(vuelo.getDestino())
                .horaSalida(vuelo.getHoraSalida())
                .fecha(vuelo.getFecha())
                .puertaEmbarque(vuelo.getPuertaEmbarque())
                .estado(vuelo.getEstado())
                .precio(vuelo.getPrecio())
                .capacidad(vuelo.getCapacidad())
                .build();
    }

    // 🔹 DTO → Entity (por si luego creas vuelos)
    public static Vuelo toEntity(VueloResponse dto) {

        if (dto == null) {
            return null;
        }

        return Vuelo.builder()
                .id(dto.getId())
                .numeroVuelo(dto.getNumeroVuelo())
                .aerolinea(dto.getAerolinea())
                .origen(dto.getOrigen())
                .destino(dto.getDestino())
                .horaSalida(dto.getHoraSalida())
                .fecha(dto.getFecha())
                .puertaEmbarque(dto.getPuertaEmbarque())
                .estado(dto.getEstado())
                .precio(dto.getPrecio())
                .capacidad(dto.getCapacidad())
                .build();
    }
}