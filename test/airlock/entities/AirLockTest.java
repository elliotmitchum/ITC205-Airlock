package airlock.entities;

import airlock.exceptions.AirLockException;
import airlock.exceptions.DoorException;
import airlock.exceptions.OverrideException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AirLockTest {

    @Test
    // throws AirLockNotSealedException if airlock state is not SEALED.
    void shouldNotEqualiseInternalOnOpen() {
        try {
            double externalPressure = 20;
            PressureSensor lockSensor = new PressureSensor(0);
            Door internalDoor = new Door(new PressureSensor(externalPressure), lockSensor, DoorState.CLOSED);
            Door externalDoor = new Door(new PressureSensor(10), lockSensor, DoorState.OPEN);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.equaliseInternalPressure();
            fail("Should not equalise pressure if doors are open.");
            assertEquals(lockSensor.getPressure(), externalPressure);
        } catch (AirLockException e) {
            assertNotNull(e);
        }
    }

    @Test
    // throws AirLockNotSealedException if airlock state is not SEALED.
    void shouldNotEqualiseExternalOnOpen() {
        try {
            double externalPressure = 20;
            PressureSensor lockSensor = new PressureSensor(0);
            Door internalDoor = new Door(new PressureSensor(externalPressure), lockSensor, DoorState.OPEN);
            Door externalDoor = new Door(new PressureSensor(10), lockSensor, DoorState.CLOSED);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.equaliseExternalPressure();
            fail("Should not equalise pressure if doors are open.");
            assertEquals(lockSensor.getPressure(), externalPressure);
        } catch (AirLockException e) {
            assertNotNull(e);
        }
    }

    @Test
    // Equalises lock pressure with internal pressure.
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
    // Equalises lock pressure with external pressure.
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
    // Opens internal door, any door exceptions passed through, sets airlock state to OPEN.
    void canOpenEqualPressureInnerDoor() {
        try {
            PressureSensor lockSensor = new PressureSensor(0);
            Door internalDoor = new Door(new PressureSensor(20), lockSensor, DoorState.CLOSED);
            Door externalDoor = new Door(new PressureSensor(10), lockSensor, DoorState.CLOSED);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.equaliseInternalPressure();
            airLock.openInnerDoor();
            assertEquals(AirLockState.OPEN, airLock.getState());
        } catch (DoorException | AirLockException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    // returns true if outer door  is CLOSED otherwise false
    void shouldShowOuterDoorClosed() {
        try {
            PressureSensor lockSensor = new PressureSensor(0);
            Door internalDoor = new Door(new PressureSensor(20), lockSensor, DoorState.CLOSED);
            Door externalDoor = new Door(new PressureSensor(10), lockSensor, DoorState.OPEN);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            assertEquals(false, airLock.isOuterDoorClosed());
            airLock.closeOuterDoor();
            assertEquals(true, airLock.isOuterDoorClosed());
        } catch (DoorException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    // throws OtherDoorOpenException if override state is AUTO and external door open.
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
    // Opens external door, any door exceptions passed through, sets airlock state to OPEN.
    void canOpenEqualPressureOuterDoor() {
        try {
            PressureSensor lockSensor = new PressureSensor(0);
            Door internalDoor = new Door(new PressureSensor(20), lockSensor, DoorState.CLOSED);
            Door externalDoor = new Door(new PressureSensor(10), lockSensor, DoorState.CLOSED);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.equaliseExternalPressure();
            airLock.openOuterDoor();
            assertEquals(AirLockState.OPEN, airLock.getState());
        } catch (DoorException | AirLockException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    // Returns true if inner door is CLOSED otherwise false.
    void shouldShowInnerDoorClose() {
        try {
            PressureSensor lockSensor = new PressureSensor(0);
            Door internalDoor = new Door(new PressureSensor(20), lockSensor, DoorState.OPEN);
            Door externalDoor = new Door(new PressureSensor(10), lockSensor, DoorState.CLOSED);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            assertEquals(false, airLock.isInnerDoorClosed());
            airLock.closeInnerDoor();
            assertEquals(true, airLock.isInnerDoorClosed());
        } catch (DoorException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    // Closes external door, any door exceptions passed through, if internal door closed sets airlock state to SEALED.
    void canCloseEqualPressureOuterDoor() {
        try {
            PressureSensor lockSensor = new PressureSensor(0);
            Door internalDoor = new Door(new PressureSensor(20), lockSensor, DoorState.CLOSED);
            Door externalDoor = new Door(new PressureSensor(10), lockSensor, DoorState.OPEN);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.closeOuterDoor();
            assertEquals(AirLockState.SEALED, airLock.getState());
        } catch (DoorException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    // Closes internal door, any door exceptions passed through.
    void canCloseEqualPressureInnerDoor() {
        try {
            PressureSensor lockSensor = new PressureSensor(0);
            Door internalDoor = new Door(new PressureSensor(20), lockSensor, DoorState.OPEN);
            Door externalDoor = new Door(new PressureSensor(10), lockSensor, DoorState.CLOSED);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.closeInnerDoor();
            assertEquals(AirLockState.SEALED, airLock.getState());
        } catch (DoorException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    // Throws OtherDoorOpenException if override state is AUTO and internal door open.
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
    // Toggles overrideState between MANUAL and AUTO.
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
    // Returns true if overrideState is MANUAL otherwise false.
    void shouldShowManualOverride() {
        try {
            PressureSensor lockSensor = new PressureSensor(0);
            Door internalDoor = new Door(new PressureSensor(0), lockSensor, DoorState.CLOSED);
            Door externalDoor = new Door(new PressureSensor(0), lockSensor, DoorState.CLOSED);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            assertEquals(false, airLock.isInManualOverride());
            airLock.toggleOverride();
            assertEquals(true, airLock.isInManualOverride());
        } catch (OverrideException e) {
            fail(e.getMessage());
        }
    }

    @Test
    // Toggles overrideState between AUTO and MANUAL.
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
    // Throws OverrideException if airlock state is not SEALED.
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