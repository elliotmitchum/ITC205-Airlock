package airlock.entities;

import airlock.exceptions.AirLockException;
import airlock.exceptions.DoorException;
import airlock.exceptions.OverrideException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AirLockTest {

    @Test
    void setsLockSensorPressureOnEqualiseInnerPressure() {
        try {
            double externalPressure = 20;
            PressureSensor lockSensor = new PressureSensor(0);
            Door internalDoor = new Door(new PressureSensor(externalPressure), lockSensor, DoorState.CLOSED);
            Door externalDoor = new Door(new PressureSensor(10), lockSensor, DoorState.CLOSED);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.equaliseInternalPressure();
            assertEquals(lockSensor.getPressure(), externalPressure);
        } catch (AirLockException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void setsLockSensorPressureOnEqualiseOuterPressure() {
        try {
            double externalPressure = 10;
            PressureSensor lockSensor = new PressureSensor(0);
            Door internalDoor = new Door(new PressureSensor(20), lockSensor, DoorState.CLOSED);
            Door externalDoor = new Door(new PressureSensor(externalPressure), lockSensor, DoorState.CLOSED);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.equaliseExternalPressure();
            assertEquals(lockSensor.getPressure(), externalPressure);
        } catch (AirLockException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void canOpenEqualPressureInnerDoor() {
        try {
            PressureSensor lockSensor = new PressureSensor(0);
            Door internalDoor = new Door(new PressureSensor(20), lockSensor, DoorState.CLOSED);
            Door externalDoor = new Door(new PressureSensor(10), lockSensor, DoorState.CLOSED);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.equaliseInternalPressure();
            airLock.openInnerDoor();
            assertEquals(false, airLock.isInnerDoorClosed());
            assertEquals(true, airLock.isOuterDoorClosed());
        } catch (DoorException | AirLockException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void cannotOpenUnequalPressureInnerDoor() {
        try {
            PressureSensor lockSensor = new PressureSensor(0);
            Door internalDoor = new Door(new PressureSensor(20), lockSensor, DoorState.CLOSED);
            Door externalDoor = new Door(new PressureSensor(10), lockSensor, DoorState.CLOSED);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.openInnerDoor();
            fail("Should not open internal door with unequal pressure");
        } catch (DoorException | AirLockException e) {
            assertNotNull(e);
        }
    }

    @Test
    void cannotOpenOpenedOuterDoor() {
        try {
            PressureSensor lockSensor = new PressureSensor(0);
            Door internalDoor = new Door(new PressureSensor(20), lockSensor, DoorState.CLOSED);
            Door externalDoor = new Door(new PressureSensor(10), lockSensor, DoorState.OPEN);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.openInnerDoor();
            fail("Should not open internal door with opened external door");
        } catch (DoorException | AirLockException e) {
            assertNotNull(e);
        }
    }

    @Test
    void canOpenEqualPressureOuterDoor() {
        try {
            PressureSensor lockSensor = new PressureSensor(0);
            Door internalDoor = new Door(new PressureSensor(20), lockSensor, DoorState.CLOSED);
            Door externalDoor = new Door(new PressureSensor(10), lockSensor, DoorState.CLOSED);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.equaliseExternalPressure();
            airLock.openOuterDoor();
            assertEquals(false, airLock.isOuterDoorClosed());
            assertEquals(true, airLock.isInnerDoorClosed());
        } catch (DoorException | AirLockException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void cannotOpenUnequalPressureOuterDoor() {
        try {
            PressureSensor lockSensor = new PressureSensor(0);
            Door internalDoor = new Door(new PressureSensor(20), lockSensor, DoorState.CLOSED);
            Door externalDoor = new Door(new PressureSensor(10), lockSensor, DoorState.CLOSED);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.openOuterDoor();
            fail("Should not open outer door with unequal pressure");
        } catch (DoorException | AirLockException e) {
            assertNotNull(e);
        }
    }

    @Test
    void cannotOpenOpenedInnerDoor() {
        try {
            PressureSensor lockSensor = new PressureSensor(0);
            Door internalDoor = new Door(new PressureSensor(20), lockSensor, DoorState.OPEN);
            Door externalDoor = new Door(new PressureSensor(10), lockSensor, DoorState.CLOSED);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.openOuterDoor();
            fail("Should not open outer door with opened external door");
        } catch (DoorException | AirLockException e) {
            assertNotNull(e);
        }
    }

    @Test
    void canOverrideToManualWithClosedDoors() {
        try {
            PressureSensor lockSensor = new PressureSensor(0);
            Door internalDoor = new Door(new PressureSensor(0), lockSensor, DoorState.CLOSED);
            Door externalDoor = new Door(new PressureSensor(0), lockSensor, DoorState.CLOSED);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.toggleOverride();
            assertEquals(true, airLock.isInManualOverride());
        } catch (OverrideException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void canOverrideToAutoWithClosedDoors() {
        try {
            PressureSensor lockSensor = new PressureSensor(0);
            Door internalDoor = new Door(new PressureSensor(0), lockSensor, DoorState.CLOSED);
            Door externalDoor = new Door(new PressureSensor(0), lockSensor, DoorState.CLOSED);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.toggleOverride();
            airLock.toggleOverride();
            assertEquals(OverrideState.AUTO, airLock.getOverrideState());
        } catch (OverrideException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void cannotOverrideOpenDoors() {
        try {
            PressureSensor lockSensor = new PressureSensor(0);
            Door internalDoor = new Door(new PressureSensor(0), lockSensor, DoorState.OPEN);
            Door externalDoor = new Door(new PressureSensor(0), lockSensor, DoorState.CLOSED);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.toggleOverride();
            fail("Should not override with open doors.");
        } catch (OverrideException e) {
            assertNotNull(e);
        }
    }

}