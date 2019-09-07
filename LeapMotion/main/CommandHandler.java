package main;

public class CommandHandler {
	final static String RESET = "r";
	final static String KILL = "k";
	final static String WAKE = "w";
	public static boolean asleep = false;
	
	public boolean command(String command) {
		switch(command) {
			case "k": sleep(); return true;
			case "w": wake(); return true;
			case "r": SerialHandler.sendCode(RESET); return true;
			default: return false;
		}
	}
	
	public void sleep() {
		SerialHandler.sendCode(RESET);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		SerialHandler.sendCode(KILL);
		asleep = true;
		System.out.println("SLEEP");
	}
	
	public static void wake() {
		SerialHandler.sendCode(WAKE);
		asleep = false;
		System.out.println("WAKE");
	}
	
}
