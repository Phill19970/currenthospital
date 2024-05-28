package griddynamics.capstone.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = CounterService.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CounterServiceTest {

    @Autowired
    private CounterService counterService;

    @Test
    public void testIncrementAndGet() {
        assertEquals(1, counterService.incrementAndGet());
    }

    @Test
    public void testIncrementAndGetAgain() {
        assertEquals(1, counterService.incrementAndGet());
    }
}
