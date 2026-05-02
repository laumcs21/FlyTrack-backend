package com.flytrack;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.flytrack.Business.Service.EmailService;

@SpringBootTest
class FlytrackApplicationTests {

    @MockBean
    private EmailService emailService;

    @Test
    void contextLoads() {
        // Si llegamos aquí, el contexto de Spring cargó exitosamente
    }
}
