package airlock.usecases;

import airlock.entities.AirLock;
import airlock.entities.IAirLock;
import airlock.exceptions.AirLockException;
import airlock.exceptions.DoorException;

public class OpenOuterDoorCTL {
	
	private IAirLock airLock;

	public OpenOuterDoorCTL(IAirLock airLock) {
		this.airLock = airLock;
	}

	public void openOuterDoor() {
		// TODO Auto-generated method stub
	}

}
