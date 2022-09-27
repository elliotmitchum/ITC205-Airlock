package airlock.entities;

import airlock.exceptions.DoorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoorTest {

    @Test
    // Throws a DoorAlreadyOpenException if Door is already open.
    void openOpened() {
        try {
            PressureSensor internal = new PressureSensor(10);
            PressureSensor external = new PressureSensor(10);
            Door door = new Door(external, internal, DoorState.OPEN);
            door.open();
            fail("Open door cannot be opened again.");
        } catch (DoorException e) {
            assertNotNull(e);
        }
    }

    @Test
    // Throws a DoorAlreadyOpenException exception if Door is already closed.
    void closeClosed() {
        try {
            PressureSensor internal = new PressureSensor(10);
            PressureSensor external = new PressureSensor(10);
            Door door = new Door(external, internal, DoorState.CLOSED);
            door.close();
            fail("Closed door cannot be closed again.");
        } catch (DoorException e) {
            assertNotNull(e);
        }
    }

    @Test
    // Opens door (Door state becomes OPEN) if internal and internal pressures are within TOLERANCE.
    void openWithInInternalTolerance() {
        try {
            PressureSensor internal = new PressureSensor(10 + 0.001);
            PressureSensor external = new PressureSensor(10);
            Door door = new Door(external, internal, DoorState.CLOSED);
            door.open();
            assertEquals(DoorState.OPEN, door.getState());
        } catch (DoorException e) {
            fail(e.getMessage());
        }
    }

    @Test
    // Throws an UnequalPressureException internal outside of TOLERANCE.
    void openWithOutInternalTolerance() {
        try {
            PressureSensor internal = new PressureSensor(10 + 0.002);
            PressureSensor external = new PressureSensor(10 );
            Door door = new Door(external, internal, DoorState.CLOSED);
            door.open();
            fail("Incorrectly opened outside of tolerance");
        } catch (DoorException e) {
            assertNotNull(e);
        }
    }

    @Test
    // Opens door (Door state becomes OPEN) if internal and external pressures are within TOLERANCE.
    void openWithInExternalTolerance() {
        try {
            PressureSensor internal = new PressureSensor(10);
            PressureSensor external = new PressureSensor(10 + 0.001);
            Door door = new Door(external, internal, DoorState.CLOSED);
            door.open();
            assertEquals(DoorState.OPEN, door.getState());
        } catch (DoorException e) {
            fail(e.getMessage());
        }
    }

    @Test
    // Throws an UnequalPressureException external outside of TOLERANCE.
    void openWithOutExternalTolerance() {
        try {
            PressureSensor internal = new PressureSensor(10);
            PressureSensor external = new PressureSensor(10 + 0.002);
            Door door = new Door(external, internal, DoorState.CLOSED);
            door.open();
            fail("Incorrectly opened outside of tolerance");
        } catch (DoorException e) {
            assertNotNull(e);
        }
    }

    @Test
    // Closes door (Door state becomes CLOSED).
    void close() {
        try {
            PressureSensor internal = new PressureSensor(10);
            PressureSensor external = new PressureSensor(10);
            Door door = new Door(external, internal, DoorState.OPEN);
            door.close();
            assertEquals(DoorState.CLOSED, door.getState());
        } catch (DoorException e) {
            fail(e.getMessage());
        }
    }

    @Test
    // Should return the pressure reading from the external sensor.
    void shouldGetExternalPressure() {
        double externalPressure = 20;
        PressureSensor internal = new PressureSensor(10);
        PressureSensor external = new PressureSensor(externalPressure);
        Door door = new Door(external, internal, DoorState.OPEN);
        assertEquals(externalPressure, door.getExternalPressure());
    }

    @Test
    // Should return the pressure reading from the internal sensor.
    void shouldGetInternalPressure() {
        double internalPressure = 20;
        PressureSensor internal = new PressureSensor(internalPressure);
        PressureSensor external = new PressureSensor(20);
        Door door = new Door(external, internal, DoorState.OPEN);
        assertEquals(internalPressure, door.getExternalPressure());
    }

    @Test
    // should return TRUE if door state is OPEN
    void shouldDetectOpenDoor() {
        PressureSensor internal = new PressureSensor(10);
        PressureSensor external = new PressureSensor(20);
        Door door = new Door(external, internal, DoorState.OPEN);
        assertEquals(true, door.isOpen());
        assertEquals(false, door.isClosed());
    }

    @Test
    // should return False if door state is CLOSED
    void shouldDetectClosedDoor() {
        PressureSensor internal = new PressureSensor(10);
        PressureSensor external = new PressureSensor(20);
        Door door = new Door(external, internal, DoorState.CLOSED);
        assertEquals(true, door.isClosed());
        assertEquals(false, door.isOpen());
    }

    @Test
    // Returns the current DoorState – OPEN or CLOSED
    void shouldGetDoorState() {
        PressureSensor internal = new PressureSensor(10);
        PressureSensor external = new PressureSensor(20);
        Door door = new Door(external, internal, DoorState.CLOSED);
        assertEquals(DoorState.CLOSED, door.getState());
    }

}