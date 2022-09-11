package airlock.entities;

public class PressureSensor implements IPressureSensor {
	
	double pressure;
	
	public PressureSensor(double initialPressure) {
		this.pressure = initialPressure;
	}
	
	public double getPressure() {
		return this.pressure;
	}
	
	public void setPressure(double newPressure) {
		this.pressure = newPressure;
	}

}
