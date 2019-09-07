package main;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Gesture.Type;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;

class LeapControl extends Listener {
	DescisionHandler decisionHandler = new DescisionHandler();
	
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
		if(hand.confidence() == 1 && Math.abs(hand.palmPosition().getX()) < 50 && Math.abs(hand.palmPosition().getZ()) < 50) {
			decisionHandler.sendPositions(frame.fingers());
		}
	}
}
