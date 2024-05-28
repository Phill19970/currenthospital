package griddynamics.capstone;

import griddynamics.capstone.service.CounterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = CounterService.class)
@TestPropertySource(properties = {"custom.property=value"})
public class SomeServiceTest {

    @Value("${custom.property}")
    private String customProperty;

    @Test
    public void testCustomProperty() {
        assertEquals("value", customProperty);
    }
}