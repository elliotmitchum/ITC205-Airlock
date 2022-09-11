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
		this.outerDoor = externalDoor;
		this.innerDoor = internalDoor;
		this.lockSensor = lockSensor;
		this.state = externalDoor.isOpen() || internalDoor.isOpen() ? AirLockState.OPEN : AirLockState.SEALED;
		this.overrideState = OverrideState.AUTO;
	}
	
	public String toString() {
		return String.format(
			"Airlock: state: %s, override: %s, AirLock Pressure: %3.1f",
			state, overrideState, lockSensor.getPressure());
	}
	
	@Override
	public void openOuterDoor() throws OtherDoorOpenException, DoorException {
		if (this.overrideState == OverrideState.AUTO && this.innerDoor.isOpen()) {
			throw new OtherDoorOpenException("Inner door must be closed.");
		}

		this.outerDoor.open();
		this.state = AirLockState.OPEN;
	}
	
	@Override
	public void closeOuterDoor() throws DoorException {
		this.outerDoor.close();
		this.state = AirLockState.SEALED;
 	}
	
	@Override
	public void openInnerDoor() throws OtherDoorOpenException, DoorException {
		if (this.overrideState == OverrideState.AUTO && this.outerDoor.isOpen()) {
			throw new OtherDoorOpenException("Outer door must be closed.");
		}

		this.innerDoor.open();
		this.state = AirLockState.OPEN;
	}
	
	@Override
	public void closeInnerDoor() throws DoorException {
		this.innerDoor.close();
		this.state = AirLockState.SEALED;
	}
	
	@Override
	public void equaliseInternalPressure() throws AirLockException {
		if (this.state != AirLockState.SEALED) {
			throw new AirLockNotSealedException("Airlock must be sealed");
		}

		double externalPressure = innerDoor.getExternalPressure();
		this.lockSensor.setPressure(externalPressure);
	}

	@Override
	public void equaliseExternalPressure()  throws AirLockException {
		if (this.state != AirLockState.SEALED) {
			throw new AirLockNotSealedException("Airlock must be sealed");
		}

		double externalPressure = outerDoor.getExternalPressure();
		this.lockSensor.setPressure(externalPressure);
	}

	@Override
	public void toggleOverride() throws OverrideException{
		if (innerDoor.isOpen() || outerDoor.isOpen()) {
			throw new OverrideException("Doors must first be closed");
		}

		if (this.overrideState == OverrideState.MANUAL) {
			this.overrideState = OverrideState.AUTO;
		} else {
			this.overrideState = OverrideState.MANUAL;
		}
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
