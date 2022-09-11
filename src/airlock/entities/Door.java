package airlock.entities;

import airlock.exceptions.DoorAlreadyClosedException;
import airlock.exceptions.DoorAlreadyOpenException;
import airlock.exceptions.DoorException;
import airlock.exceptions.UnequalPressureException;

public class Door implements IDoor{
	
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
		// TODO Auto-generated method stub
	}
	
	@Override
	public void close() throws DoorException {
		// TODO Auto-generated method stub
	}

	@Override
	public double getExternalPressure() {
		// TODO Auto-generated method stub
		return 0.0;
	}

	@Override
	public double getInternalPressure() {
		// TODO Auto-generated method stub
		return 0.0;
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
