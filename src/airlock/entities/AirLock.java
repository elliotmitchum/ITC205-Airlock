package airlock.entities;

import airlock.exceptions.AirLockException;
import airlock.exceptions.AirLockNotSealedException;
import airlock.exceptions.DoorException;
import airlock.exceptions.OverrideException;
import airlock.exceptions.OtherDoorOpenException;

public class AirLock implements IAirLock{
	
	private IDoor outerDoor;
	private IDoor innerDoor;
	private IPressureSensor lockSensor;
	
	private AirLockState state;	
	private OverrideState overrideState;
	

	public AirLock(IDoor externalDoor, IDoor internalDoor,
			IPressureSensor lockSensor) {
		// TODO Auto-generated method stub
	}
	
	public String toString() {
		return String.format(
			"Airlock: state: %s, override: %s, AirLock Pressure: %3.1f",
			state, overrideState, lockSensor.getPressure());
	}
	
	@Override
	public void openOuterDoor() throws OtherDoorOpenException, DoorException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void closeOuterDoor() throws DoorException {
		// TODO Auto-generated method stub
 	}
	
	@Override
	public void openInnerDoor() throws OtherDoorOpenException, DoorException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void closeInnerDoor() throws DoorException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void equaliseInternalPressure() throws AirLockException {
		// TODO Auto-generated method stub
	}

	@Override
	public void equaliseExternalPressure()  throws AirLockException {
		// TODO Auto-generated method stub
	}

	@Override
	public void toggleOverride() throws OverrideException{
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isOuterDoorClosed() {
		return outerDoor.isClosed();
	}

	@Override
	public boolean isInnerDoorClosed() {
		return innerDoor.isClosed();
	}

	@Override
	public boolean isInManualOverride() {
		return overrideState == OverrideState.MANUAL;
	}

	@Override
	public AirLockState getState() {
		return state;
	}

	@Override
	public OverrideState getOverrideState() {
		return overrideState;
	}

}
