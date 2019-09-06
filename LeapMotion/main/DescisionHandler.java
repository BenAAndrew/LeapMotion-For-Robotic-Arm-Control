package main;

public class DescisionHandler {
	boolean[] positions = new boolean[]{false,false,false,false,false};
	final int[] CLOSE_THRESHOLD = new int[]{75, 80, 95, 90, 80};
	final int[] OPEN_THRESHOLD = new int[]{80, 90, 100, 100, 90};
	SerialHandler serialHandler;
	
	public DescisionHandler() {
		serialHandler = new SerialHandler();
	}
	
	public void moveFinger(int index, boolean on) {
		if (CommandHandler.asleep)
			CommandHandler.wake();
		String command = String.valueOf(index);
		if(on) {
			command += "1";
			positions[index] = true;
		} else {
			command += "0";
			positions[index] = false;
		}
		serialHandler.sendCommand(command);
	}
	
	public void react(int index, float distance) {
		if(distance < CLOSE_THRESHOLD[index] && !positions[index]) {
			moveFinger(index, true);
		} else if(distance > OPEN_THRESHOLD[index] && positions[index]) {
			moveFinger(index, false);
		}
	}
}
