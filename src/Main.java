import java.util.Scanner;

import airlock.entities.AirLock;
import airlock.entities.Door;
import airlock.entities.DoorState;
import airlock.entities.IAirLock;
import airlock.entities.IDoor;
import airlock.entities.IPressureSensor;
import airlock.entities.PressureSensor;
import airlock.exceptions.AirLockException;
import airlock.exceptions.DoorException;
import airlock.usecases.OpenInnerDoorCTL;
import airlock.usecases.OpenOuterDoorCTL;

public class Main {

	public static void main(String[] args) {
		
		double externalPressure = 1.0;
		double lockPressure = 1.0;
		double internalPressure = 1.0;
		
		IPressureSensor externalSensor = new PressureSensor(externalPressure);
		IPressureSensor lockSensor     = new PressureSensor(lockPressure);
		IPressureSensor internalSensor = new PressureSensor(internalPressure);
		
		IDoor externalDoor = new Door(externalSensor, lockSensor, DoorState.CLOSED);
		IDoor internalDoor = new Door(internalSensor, lockSensor, DoorState.CLOSED);
		
		IAirLock airLock = new AirLock(externalDoor, internalDoor, lockSensor);
		
		String menuFormatString = 
		"""
					
			%s
			Internal %s
			External %s
				
					
			Select option:
			    OX - open external door
			    OI - open internal door

			    CX - close external door
			    CI - close internal door

			    SX - set external pressure
			    SI - set internal pressure
			    
			    EX - equalise lock with external pressure
				EI - equalise lock with internal pressure
			    
			    TM - toggle manual override
			    
			    Q  - quit
					
			Selection: """;
		
		Scanner scanner = new Scanner(System.in);
		//testLoop
		boolean finished = false;
		
		while (!finished) {		
			String ans = null;
			
			try {
				System.out.print(String.format(menuFormatString, 
					airLock, internalDoor, externalDoor));
				
				ans = scanner.nextLine();
				
				switch (ans.toUpperCase().strip()) {
				
					case "OX" :					
						new OpenOuterDoorCTL(airLock).openOuterDoor();
						break;
						
					case "OI" :
						if (airLock.isInManualOverride()) airLock.equaliseInternalPressure();
						new OpenInnerDoorCTL(airLock).openInnerDoor();
						break;
						
					case "CX" :
						airLock.closeOuterDoor();
						break;
						
					case "CI" :
						airLock.closeInnerDoor();
						break;
						
					case "SX" :
						System.out.println("Enter external pressure: ");
						ans = scanner.nextLine();
						double exP = Double.valueOf(ans).doubleValue();
						externalSensor.setPressure(exP);
						if (externalDoor.isOpen()) {
							lockSensor.setPressure(exP);
							if (internalDoor.isOpen()) {
								internalSensor.setPressure(exP);
							}
						}
						break;
					
					case "SI" :
						System.out.println("Enter internal pressure: ");
						ans = scanner.nextLine();
						double inP = Double.valueOf(ans).doubleValue();
						internalSensor.setPressure(inP);
						if (internalDoor.isOpen()) {
							lockSensor.setPressure(inP);
							if (externalDoor.isOpen()) {
								externalSensor.setPressure(inP);
							}
						}
						break;

					case "EX" :
						airLock.equaliseExternalPressure();
						break;

					case "EI" :
						airLock.equaliseInternalPressure();
						break;
						
					case "TM" :
						System.out.println("Toggle manual override");
						airLock.toggleOverride();
						break;
						
					case "Q" :
						finished = true;
						break;						
						
					default:
						System.out.printf("Unrecognised option: %s\n", ans);
				}		
			}
			catch (AirLockException | DoorException  e) {
				System.err.println(e.getMessage());
			}
		} //while
		scanner.close();
		System.out.println("\nExiting\n");
	}

}
