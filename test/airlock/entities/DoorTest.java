package airlock.entities;

import airlock.exceptions.DoorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoorTest {

    @Test
    void openOpened() {
        try {
            PressureSensor internal = new PressureSensor(10);
            PressureSensor external = new PressureSensor(10);
            Door door = new Door(internal, external, DoorState.OPEN);
            door.open();
            fail("Open door cannot be opened again.");
        } catch (DoorException e) {
            assertNotNull(e);
        }
    }

    @Test
    void closeClosed() {
        try {
            PressureSensor internal = new PressureSensor(10);
            PressureSensor external = new PressureSensor(10);
            Door door = new Door(internal, external, DoorState.CLOSED);
            door.close();
            fail("Closed door cannot be closed again.");
        } catch (DoorException e) {
            assertNotNull(e);
        }
    }

    @Test
    void openWithInInternalTolerance() {
        try {
            PressureSensor internal = new PressureSensor(10 + 0.001);
            PressureSensor external = new PressureSensor(10);
            Door door = new Door(internal, external, DoorState.CLOSED);
            door.open();
            assertEquals(DoorState.OPEN, door.getState());
        } catch (DoorException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void openWithOutInternalTolerance() {
        try {
            PressureSensor internal = new PressureSensor(10 + 0.002);
            PressureSensor external = new PressureSensor(10 );
            Door door = new Door(internal, external, DoorState.CLOSED);
            door.open();
            fail("Incorrectly opened outside of tolerance");
        } catch (DoorException e) {
            assertNotNull(e);
        }
    }

    @Test
    void openWithInExternalTolerance() {
        try {
            PressureSensor internal = new PressureSensor(10);
            PressureSensor external = new PressureSensor(10 + 0.001);
            Door door = new Door(internal, external, DoorState.CLOSED);
            door.open();
            assertEquals(DoorState.OPEN, door.getState());
        } catch (DoorException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void openWithOutExternalTolerance() {
        try {
            PressureSensor internal = new PressureSensor(10);
            PressureSensor external = new PressureSensor(10 + 0.002);
            Door door = new Door(internal, external, DoorState.CLOSED);
            door.open();
            fail("Incorrectly opened outside of tolerance");
        } catch (DoorException e) {
            assertNotNull(e);
        }
    }

    @Test
    void close() {
        try {
            PressureSensor internal = new PressureSensor(10);
            PressureSensor external = new PressureSensor(10);
            Door door = new Door(internal, external, DoorState.OPEN);
            door.close();
            assertEquals(DoorState.CLOSED, door.getState());
        } catch (DoorException e) {
            fail(e.getMessage());
        }
    }
}