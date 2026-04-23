package com.flytrack.Controller;

import com.flytrack.Business.Dto.VerificarCodigoRequest;
import com.flytrack.Persistence.Model.Reserva;
import com.flytrack.Business.Service.AccesoReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mis-vuelos")
public class AccesoReservaController {

    @Autowired
    private AccesoReservaService accesoService;

    @PostMapping("/solicitar-codigo")
    public void solicitarCodigo(@RequestBody Map<String, String> body) {
        String documento = body.get("documento");
        accesoService.solicitarCodigo(documento);
    }

    @PostMapping("/verificar")
    public ResponseEntity<List<Reserva>> verificar(@RequestBody VerificarCodigoRequest req) {
        return ResponseEntity.ok(
                accesoService.verificarCodigo(req.getDocumento(), req.getCodigo())
        );
    }
}
