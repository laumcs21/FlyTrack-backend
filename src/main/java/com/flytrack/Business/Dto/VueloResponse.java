package com.flytrack.Business.Dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public class VueloResponse {

    private Long id;
    private String numeroVuelo;
    private String aerolinea;
    private String origen;
    private String destino;
    private String horaSalida;
    private LocalDate fecha;
    private String puertaEmbarque;
    private String estado;
    private double precio;
    private int capacidad;

    public VueloResponse() {}

    public VueloResponse(Long id, String numeroVuelo, String aerolinea,
                         String origen, String destino, String horaSalida,
                         LocalDate fecha, String puertaEmbarque,
                         String estado, double precio, int capacidad) {

        this.id = id;
        this.numeroVuelo = numeroVuelo;
        this.aerolinea = aerolinea;
        this.origen = origen;
        this.destino = destino;
        this.horaSalida = horaSalida;
        this.fecha = fecha;
        this.puertaEmbarque = puertaEmbarque;
        this.estado = estado;
        this.precio = precio;
        this.capacidad = capacidad;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroVuelo() { return numeroVuelo; }
    public void setNumeroVuelo(String numeroVuelo) { this.numeroVuelo = numeroVuelo; }

    public String getAerolinea() { return aerolinea; }
    public void setAerolinea(String aerolinea) { this.aerolinea = aerolinea; }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public String getHoraSalida() { return horaSalida; }
    public void setHoraSalida(String horaSalida) { this.horaSalida = horaSalida; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getPuertaEmbarque() { return puertaEmbarque; }
    public void setPuertaEmbarque(String puertaEmbarque) { this.puertaEmbarque = puertaEmbarque; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
}