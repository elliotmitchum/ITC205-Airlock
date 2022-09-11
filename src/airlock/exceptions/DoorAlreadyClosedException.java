package airlock.exceptions;

public class DoorAlreadyClosedException extends DoorException {

	public DoorAlreadyClosedException(String message) {
		super(message);
	}

}
