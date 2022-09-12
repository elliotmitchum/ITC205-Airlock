package unit.entities;

import airlock.entities.PressureSensor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PressureSensorTest {

    @Test
    void getPressure() {
        PressureSensor sensor = new PressureSensor(10);
        assertEquals(sensor.getPressure(), 10);
    }

    @Test
    void setPressure() {
        PressureSensor sensor = new PressureSensor(0);
        sensor.setPressure(10);
        assertEquals(sensor.getPressure(), 10);
    }
}