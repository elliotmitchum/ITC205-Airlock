package airlock.entities;

import java.util.HashMap;
import java.util.Map;

public enum OverrideState {
	MANUAL, AUTO;
/*
	private static final Map<OverrideState, String> VALUE_REPR_MAP = new HashMap<OverrideState, String>();

	static {
		VALUE_REPR_MAP.put(OverrideState.ON, "ON");
		VALUE_REPR_MAP.put(OverrideState.OFF, "OFF");
	}

	public String toString() {
		return VALUE_REPR_MAP.get(this);
	}
*/
}