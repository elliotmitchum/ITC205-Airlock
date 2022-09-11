package airlock.entities;

import airlock.exceptions.DoorAlreadyClosedException;
import airlock.exceptions.DoorAlreadyOpenException;
import airlock.exceptions.DoorException;
import airlock.exceptions.UnequalPressureException;

import static java.lang.Math.abs;

public class Door implements IDoor {

	private static double TOLERANCE = 0.001;
	
	IPressureSensor internalSensor;
	IPressureSensor externalSensor;
	
	private DoorState state;
	
	public Door(IPressureSensor externalSensor, IPressureSensor internalSensor, DoorState initialState) {
		this.externalSensor = externalSensor;
		this.internalSensor = internalSensor;
		this.state = initialState;
	}
	
	@Override
	public void open() throws DoorException {
		double diff = abs(internalSensor.getPressure() - externalSensor.getPressure());

		if (diff > TOLERANCE) {
			throw new UnequalPressureException("Internal and external pressure is unequal.");
		}

		if (this.state == DoorState.OPEN) {
			throw new DoorAlreadyOpenException("Door already open.");
		}

		this.state = DoorState.OPEN;
	}
	
	@Override
	public void close() throws DoorException {
		if (this.state == DoorState.CLOSED) {
			throw new DoorAlreadyClosedException("Door already closed.");
		}

		this.state = DoorState.CLOSED;
	}

	@Override
	public double getExternalPressure() {
		return this.externalSensor.getPressure();
	}

	@Override
	public double getInternalPressure() {
		return this.internalSensor.getPressure();
	}

	@Override
	public boolean isOpen() {
		return state == DoorState.OPEN;
	}

	@Override
	public boolean isClosed() {
		return state == DoorState.CLOSED;
	}

	@Override
	public DoorState getState() {
		return state;
	}
	
	public String toString() {
		return String.format(
			"Door: state: %s, external pressure: %3.1f bar, internal pressure: %3.1f bar", 
			state, externalSensor.getPressure(), internalSensor.getPressure());
	}
	

}
