package integration.usescases;

import airlock.entities.AirLock;
import airlock.entities.Door;
import airlock.entities.DoorState;
import airlock.entities.PressureSensor;
import airlock.exceptions.AirLockException;
import airlock.exceptions.DoorException;
import airlock.usecases.OpenOuterDoorCTL;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenOuterDoorCTLTest {

    @Test
    // Should open outer door on manual.
    void shouldOpenDoorOnManual() {
        try {
            PressureSensor lockSensor = new PressureSensor(0);
            Door internalDoor = new Door(new PressureSensor(0), lockSensor, DoorState.CLOSED);
            Door externalDoor = new Door(new PressureSensor(0), lockSensor, DoorState.CLOSED);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.toggleOverride();
            new OpenOuterDoorCTL(airLock).openOuterDoor();
            assertEquals(false, airLock.isOuterDoorClosed());
        } catch (DoorException | AirLockException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    // Should open outer door on auto.
    void shouldOpenDoorOnAuto() {
        try {
            PressureSensor lockSensor = new PressureSensor(0);
            Door internalDoor = new Door(new PressureSensor(0), lockSensor, DoorState.CLOSED);
            Door externalDoor = new Door(new PressureSensor(0), lockSensor, DoorState.CLOSED);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            new OpenOuterDoorCTL(airLock).openOuterDoor();
            assertEquals(false, airLock.isOuterDoorClosed());
        } catch (DoorException | AirLockException e) {
            throw new RuntimeException(e);
        }
    }

}