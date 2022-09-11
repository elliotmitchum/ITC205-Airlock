package airlock.usecases;

import airlock.entities.IAirLock;
import airlock.entities.OverrideState;
import airlock.exceptions.AirLockException;
import airlock.exceptions.DoorException;

public class OpenInnerDoorCTL {
	
	private IAirLock airLock;

	public OpenInnerDoorCTL(IAirLock airLock) {
		this.airLock = airLock;
	}

	public void openInnerDoor() throws AirLockException, DoorException {
		if (this.airLock.getOverrideState() == OverrideState.MANUAL) {
			this.airLock.openInnerDoor();
		} else {
			if (!this.airLock.isOuterDoorClosed()) {
				this.airLock.closeOuterDoor();
			}

			this.airLock.equaliseInternalPressure();
			this.airLock.openInnerDoor();
		}
	}

}
