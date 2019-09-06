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
	
	public int getFingerIndex(Finger.Type type) {
		switch(type) {
			case TYPE_THUMB: return 0;
			case TYPE_INDEX: return 1;
			case TYPE_MIDDLE: return 2;
			case TYPE_RING: return 3;
			default: return 4;
		}
	}
	
	@Override
	public void onFrame(Controller controller) {
		Frame frame = controller.frame();	
		Hand hand = frame.hands().get(0);
		for(Finger finger : frame.fingers()) {
			float distance = finger.tipPosition().distanceTo(hand.palmPosition());
			int fingerIndex = getFingerIndex(finger.type());
			decisionHandler.react(fingerIndex, distance);
		}
	}
}
