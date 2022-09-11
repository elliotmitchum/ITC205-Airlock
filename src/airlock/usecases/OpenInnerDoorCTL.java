package airlock.usecases;

import airlock.entities.IAirLock;
import airlock.exceptions.AirLockException;
import airlock.exceptions.DoorException;

public class OpenInnerDoorCTL {
	
	private IAirLock airLock;

	public OpenInnerDoorCTL(IAirLock airLock) {
		this.airLock = airLock;
	}

	public void openInnerDoor() {
		// TODO Auto-generated method stub
	}

}
