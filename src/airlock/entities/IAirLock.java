package airlock.entities;

import airlock.exceptions.AirLockException;
import airlock.exceptions.DoorException;

public interface IAirLock {

	void openOuterDoor() throws AirLockException, DoorException;
	void closeOuterDoor() throws DoorException;

	void openInnerDoor() throws AirLockException, DoorException;
	void closeInnerDoor() throws DoorException;

	void equaliseInternalPressure() throws AirLockException;
	void equaliseExternalPressure() throws AirLockException;
	
	void toggleOverride() throws AirLockException;
	
	boolean isInManualOverride();
	boolean isOuterDoorClosed();
	boolean isInnerDoorClosed();

	AirLockState getState();			//for testing
	OverrideState getOverrideState();	//for testing
}