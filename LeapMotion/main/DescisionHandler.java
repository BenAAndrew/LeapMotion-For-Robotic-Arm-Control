package main;

import com.leapmotion.leap.Finger;
import com.leapmotion.leap.FingerList;

public class DescisionHandler {
	boolean[] positions = new boolean[]{false,false,false,false,false};
	char[] lastState = new char[]{'0','0','0','0','0'};
	SerialHandler serialHandler;
	
	public DescisionHandler() {
		serialHandler = new SerialHandler();
	}
	
	public int getFingerIndex(Finger.Type type) {
		switch(type) {
			case TYPE_THUMB: return 0;
			case TYPE_INDEX: return 1;
			case TYPE_MIDDLE: return 2;
			case TYPE_RING: return 3;
			default: return 4;
		}
	}
	
	public boolean matchesLastState(char[] state) {
		for(int i = 0; i < 5; i++) {
			if(state[i] != lastState[i]) {
				return false;
			}
		}
		return true;
	}
	
	public void sendPositions(FingerList fingers) {
		char[] command = new char[]{'0','0','0','0','0'};
		for(Finger finger : fingers) {
			boolean closed = !finger.isExtended();
			if(closed) {
				int fingerIndex = getFingerIndex(finger.type());
				command[fingerIndex] = '1';
			}	
		}
		if(!matchesLastState(command)) {
			serialHandler.sendCommand(new String(command));
			lastState = command;
		}		
	}
}
