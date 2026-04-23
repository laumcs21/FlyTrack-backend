package com.flytrack.Business.Service;

import com.flytrack.Persistence.Model.CodigoVerificacion;
import com.flytrack.Persistence.Model.Reserva;
import com.flytrack.Persistence.Repository.CodigoVerificacionRepository;
import com.flytrack.Persistence.Repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccesoReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private CodigoVerificacionRepository codigoRepo;

    @Autowired
    private EmailService emailService;

    public void solicitarCodigo(String documento) {

        List<Reserva> reservas = reservaRepository.findByPasajeroNumeroDocumento(documento);

        if (reservas.isEmpty()) {
            throw new RuntimeException("No existen reservas asociadas a este ID");
        }

        String email = reservas.get(0).getPasajero().getCorreo();

        String codigo = CodigoUtil.generarCodigo();

        CodigoVerificacion cv = new CodigoVerificacion();
        cv.setEmail(email);
        cv.setCodigo(codigo);
        cv.setExpiracion(LocalDateTime.now().plusMinutes(5));
        cv.setUsado(false);
        cv.setIntentos(0);

        codigoRepo.save(cv);

        emailService.enviarCodigoVerificacion(email, codigo);
    }

    public List<Reserva> verificarCodigo(String documento, String codigoIngresado) {

        List<Reserva> reservas = reservaRepository.findByPasajeroNumeroDocumento(documento);

        if (reservas.isEmpty()) {
            throw new RuntimeException("No existen reservas asociadas a este ID");
        }

        String email = reservas.get(0).getPasajero().getCorreo();

        CodigoVerificacion cv = codigoRepo.findTopByEmailOrderByIdDesc(email)
                .orElseThrow(() -> new RuntimeException("No hay código generado"));

        if (cv.isUsado()) {
            throw new RuntimeException("Código ya utilizado");
        }

        if (cv.getExpiracion().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Código expirado");
        }

        if (!cv.getCodigo().equals(codigoIngresado)) {
            cv.setIntentos(cv.getIntentos() + 1);
            codigoRepo.save(cv);
            throw new RuntimeException("Código incorrecto");
        }

        cv.setUsado(true);
        codigoRepo.save(cv);

        return reservas;
    }
}
