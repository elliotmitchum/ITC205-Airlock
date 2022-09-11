package airlock.entities;

import airlock.exceptions.AirLockException;
import airlock.exceptions.DoorException;
import airlock.exceptions.OverrideException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AirLockTest {

    @Test
    // throws AirLockNotSealedException if airlock state is not SEALED.
    void shouldNotEqualiseInternalOnOpen() {
        try {
            PressureSensor lockSensor = mock(PressureSensor.class);
            Door internalDoor = mock(Door.class);
            Door externalDoor = mock(Door.class);
            when(internalDoor.isOpen()).thenReturn(true);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.equaliseInternalPressure();
            fail("Should not equalise pressure if doors are open.");
        } catch (AirLockException e) {
            assertNotNull(e);
        }
    }

    @Test
    // throws AirLockNotSealedException if airlock state is not SEALED.
    void shouldNotEqualiseExternalOnOpen() {
        try {
            PressureSensor lockSensor = mock(PressureSensor.class);
            Door internalDoor = mock(Door.class);
            Door externalDoor = mock(Door.class);
            when(externalDoor.isOpen()).thenReturn(true);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.equaliseExternalPressure();
            fail("Should not equalise pressure if doors are open.");
        } catch (AirLockException e) {
            assertNotNull(e);
        }
    }

    @Test
    // Equalises lock pressure with internal pressure.
    void setsLockSensorPressureOnEqualiseInnerPressure() {
        try {
            double externalPressure = 20;
            PressureSensor lockSensor = mock(PressureSensor.class);
            Door internalDoor = mock(Door.class);
            Door externalDoor = mock(Door.class);
            when(internalDoor.getExternalPressure()).thenReturn(externalPressure);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.equaliseInternalPressure();
            verify(lockSensor, times(1)).setPressure(externalPressure);
        } catch (AirLockException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    // Equalises lock pressure with external pressure.
    void setsLockSensorPressureOnEqualiseOuterPressure() {
        try {
            double externalPressure = 20;
            PressureSensor lockSensor = mock(PressureSensor.class);
            Door internalDoor = mock(Door.class);
            Door externalDoor = mock(Door.class);
            when(externalDoor.getExternalPressure()).thenReturn(externalPressure);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.equaliseExternalPressure();
            verify(lockSensor, times(1)).setPressure(externalPressure);
        } catch (AirLockException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    // Opens internal door, any door exceptions passed through, sets airlock state to OPEN.
    void canOpenEqualPressureInnerDoor() {
        try {
            PressureSensor lockSensor = mock(PressureSensor.class);
            Door internalDoor = mock(Door.class);
            Door externalDoor = mock(Door.class);
            when(externalDoor.isOpen()).thenReturn(false);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.openInnerDoor();
            assertEquals(AirLockState.OPEN, airLock.getState());
            verify(internalDoor, times(1)).open();
        } catch (DoorException | AirLockException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    // returns true if outer door is CLOSED otherwise false
    void shouldShowOuterDoorClosed() {
        PressureSensor lockSensor = mock(PressureSensor.class);
        Door internalDoor = mock(Door.class);
        Door externalDoor = mock(Door.class);
        when(externalDoor.isClosed()).thenReturn(true, false);
        AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
        assertEquals(true, airLock.isOuterDoorClosed());
        assertEquals(false, airLock.isOuterDoorClosed());
    }

    @Test
    // throws OtherDoorOpenException if override state is AUTO and external door open.
    void cannotOpenOpenedOuterDoor() {
        try {
            PressureSensor lockSensor = mock(PressureSensor.class);
            Door internalDoor = mock(Door.class);
            Door externalDoor = mock(Door.class);
            when(externalDoor.isOpen()).thenReturn(true);
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
            PressureSensor lockSensor = mock(PressureSensor.class);
            Door internalDoor = mock(Door.class);
            Door externalDoor = mock(Door.class);
            when(internalDoor.isOpen()).thenReturn(false);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.openOuterDoor();
            assertEquals(AirLockState.OPEN, airLock.getState());
            verify(externalDoor, times(1)).open();
        } catch (DoorException | AirLockException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    // Returns true if inner door is CLOSED otherwise false.
    void shouldShowInnerDoorClose() {
        PressureSensor lockSensor = mock(PressureSensor.class);
        Door internalDoor = mock(Door.class);
        Door externalDoor = mock(Door.class);
        when(internalDoor.isClosed()).thenReturn(true, false);
        AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
        assertEquals(true, airLock.isInnerDoorClosed());
        assertEquals(false, airLock.isInnerDoorClosed());
    }

    @Test
    // Closes external door, any door exceptions passed through, if internal door closed sets airlock state to SEALED.
    void canCloseEqualPressureOuterDoor() {
        try {
            PressureSensor lockSensor = mock(PressureSensor.class);
            Door internalDoor = mock(Door.class);
            Door externalDoor = mock(Door.class);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.closeOuterDoor();
            verify(externalDoor, times(1)).close();
            assertEquals(AirLockState.SEALED, airLock.getState());
        } catch (DoorException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    // Closes internal door, any door exceptions passed through.
    void canCloseEqualPressureInnerDoor() {
        try {
            PressureSensor lockSensor = mock(PressureSensor.class);
            Door internalDoor = mock(Door.class);
            Door externalDoor = mock(Door.class);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.closeInnerDoor();
            verify(internalDoor, times(1)).close();
            assertEquals(AirLockState.SEALED, airLock.getState());
        } catch (DoorException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    // Throws OtherDoorOpenException if override state is AUTO and internal door open.
    void cannotOpenOpenedInnerDoor() {
        try {
            PressureSensor lockSensor = mock(PressureSensor.class);
            Door internalDoor = mock(Door.class);
            Door externalDoor = mock(Door.class);
            when(internalDoor.isOpen()).thenReturn(true);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.openOuterDoor();
            fail("Should not open external door with opened internal door");
        } catch (DoorException | AirLockException e) {
            assertNotNull(e);
        }
    }

    @Test
    // Toggles overrideState between MANUAL and AUTO.
    void canOverrideToManualWithClosedDoors() {
        try {
            PressureSensor lockSensor = mock(PressureSensor.class);
            Door internalDoor = mock(Door.class);
            Door externalDoor = mock(Door.class);
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
            PressureSensor lockSensor = mock(PressureSensor.class);
            Door internalDoor = mock(Door.class);
            Door externalDoor = mock(Door.class);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.toggleOverride();
            assertEquals(true, airLock.isInManualOverride());
        } catch (OverrideException e) {
            fail(e.getMessage());
        }
    }

    @Test
    // Throws OverrideException if airlock state is not SEALED.
    void cannotOverrideOpenDoors() {
        try {
            PressureSensor lockSensor = mock(PressureSensor.class);
            Door internalDoor = mock(Door.class);
            Door externalDoor = mock(Door.class);
            when(internalDoor.isOpen()).thenReturn(true);
            when(internalDoor.isClosed()).thenReturn(false);
            AirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
            airLock.toggleOverride();
            fail("Should not override with open doors.");
        } catch (OverrideException e) {
            assertNotNull(e);
        }
    }

}