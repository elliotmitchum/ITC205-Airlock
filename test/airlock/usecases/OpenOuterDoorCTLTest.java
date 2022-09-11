package airlock.usecases;

import airlock.entities.*;
import airlock.exceptions.AirLockException;
import airlock.exceptions.DoorException;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class OpenOuterDoorCTLTest {

    @Test
    // Should open outer door on manual.
    void shouldOpenDoorOnManual() {
        try {
            AirLock airLock = mock(AirLock.class);
            when(airLock.getOverrideState()).thenReturn(OverrideState.MANUAL);
            new OpenOuterDoorCTL(airLock).openOuterDoor();
            verify(airLock, times(1)).openOuterDoor();
        } catch (DoorException | AirLockException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    // Should open outer door on auto.
    void shouldOpenDoorOnAuto() {
        try {
            AirLock airLock = mock(AirLock.class);
            when(airLock.getOverrideState()).thenReturn(OverrideState.AUTO);
            when(airLock.isInnerDoorClosed()).thenReturn(false);
            new OpenOuterDoorCTL(airLock).openOuterDoor();
            verify(airLock, times(1)).closeInnerDoor();
            verify(airLock, times(1)).equaliseExternalPressure();
            verify(airLock, times(1)).openOuterDoor();
        } catch (DoorException | AirLockException e) {
            throw new RuntimeException(e);
        }
    }

}