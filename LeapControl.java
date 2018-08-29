import java.io.IOException;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;
import com.leapmotion.leap.Gesture.Type;

class LeapListener extends Listener {
	int[] positions = new int[] {50,50,50,50,50};
	
	public void onInit(Controller controller) {
		System.out.println("Leap motion started");
	}
	
	public void onConnect(Controller controller) {
		System.out.println("Leap motion connected");
		for (Type i : Gesture.Type.values()) {
			controller.enableGesture(i);
		}
	}
	
	public void onDisconnect(Controller controller) {
		System.out.println("Leap motion disconnected");
	}
	
	public void onExit(Controller controller) {
		System.out.println("Exited");
	}
	
	@Override
	public void onFrame(Controller controller) {
		Frame frame = controller.frame();	
		Hand hand = frame.hands().get(0);
		for(Finger finger : frame.fingers()) {
			int position = (((int) finger.tipPosition().distanceTo(hand.palmPosition())/10)*10);
			if(finger.type().equals(Finger.Type.TYPE_PINKY)) {
				if (positions[4] != position-40 && position-40 >= 0){
					//m/4/(postion-40)
					System.out.println("PINKY --> "+String.valueOf(position - 40));
					positions[4] = position-40;
				}
			} else if (finger.type().equals(Finger.Type.TYPE_THUMB)){
				if (positions[0] != position-50 && position-50 >= 0){
					//m/0/(postion-50)
					System.out.println("THUMB --> "+String.valueOf(position - 50));
					positions[0] = position-50;
				}
			} else if (position-60 >= 0){
				if(finger.type().equals(Finger.Type.TYPE_INDEX)) {
					if (positions[1] != position-60) {
						System.out.println("INDEX --> "+String.valueOf(position - 60));
						positions[1] = position-60;
					}
				} else if (finger.type().equals(Finger.Type.TYPE_MIDDLE)){
					if (positions[2] != position-60) {
						System.out.println("MIDDLE --> "+String.valueOf(position - 60));
						positions[2] = position-60;
					}
				} else {
					if (positions[3] != position-60) {
						System.out.println("RING --> "+String.valueOf(position - 60));
						positions[3] = position-60;
					}
				}
			}
		}
		
	}
}

public class LeapControl {

	public static void main(String[] args) {
		LeapListener listener = new LeapListener();
		Controller controller = new Controller();
		controller.addListener(listener);

		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		controller.removeListener(listener);
	}

}
