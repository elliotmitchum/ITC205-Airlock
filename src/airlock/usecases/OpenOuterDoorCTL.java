package airlock.usecases;

import airlock.entities.IAirLock;
import airlock.entities.OverrideState;
import airlock.exceptions.AirLockException;
import airlock.exceptions.DoorException;

public class OpenOuterDoorCTL {
	
	private IAirLock airLock;

	public OpenOuterDoorCTL(IAirLock airLock) {
		this.airLock = airLock;
	}

	public void openOuterDoor() throws DoorException, AirLockException {
		if (this.airLock.getOverrideState() == OverrideState.MANUAL) {
			this.airLock.openOuterDoor();
		} else {
			if (!this.airLock.isInnerDoorClosed()) {
				this.airLock.closeInnerDoor();
			}

			this.airLock.equaliseExternalPressure();
			this.airLock.openOuterDoor();
		}
	}

}
