package unit.usecases;

import airlock.entities.*;
import airlock.exceptions.AirLockException;
import airlock.exceptions.DoorException;
import airlock.exceptions.OverrideException;
import airlock.usecases.OpenInnerDoorCTL;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OpenInnerDoorCTLTest {

    @Test
    // Should open inner door on manual.
    void shouldOpenDoorOnManual() {
        try {
            AirLock airLock = mock(AirLock.class);
            when(airLock.getOverrideState()).thenReturn(OverrideState.MANUAL);
            new OpenInnerDoorCTL(airLock).openInnerDoor();
            verify(airLock, times(1)).openInnerDoor();
        } catch (DoorException | AirLockException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    // Should open inner door on auto, close outer door if open.
    void shouldOpenDoorOnAuto() {
        try {
            AirLock airLock = mock(AirLock.class);
            when(airLock.getOverrideState()).thenReturn(OverrideState.AUTO);
            when(airLock.isOuterDoorClosed()).thenReturn(false);
            new OpenInnerDoorCTL(airLock).openInnerDoor();
            verify(airLock, times(1)).closeOuterDoor();
            verify(airLock, times(1)).equaliseInternalPressure();
            verify(airLock, times(1)).openInnerDoor();
        } catch (DoorException | AirLockException e) {
            throw new RuntimeException(e);
        }
    }

}