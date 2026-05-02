package com.flytrack;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.flytrack.Business.Service.EmailService;  // ← Import correcto con mayúsculas

@SpringBootTest
class FlytrackApplicationTests {
<<<<<<< HEAD
	@MockBean
    private EmailService emailService;
	@Test
	void contextLoads() {
	}
=======

    // Mockeamos EmailService para evitar inyección de BREVO_API_KEY en tests
    @MockBean
    private EmailService emailService;
>>>>>>> c789e2c (fix: repair FlytrackApplicationTests with correct EmailService import and MockBean)

    @Test
    void contextLoads() {
        // Si llegamos aquí, el contexto de Spring cargó exitosamente
    }
}
