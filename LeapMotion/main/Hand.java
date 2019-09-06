package main;

import java.util.Scanner;

import com.leapmotion.leap.Controller;

public class Hand {

	public static void main(String[] args) {
		LeapControl listener = new LeapControl();
		Controller controller = new Controller();
		controller.addListener(listener);
		CommandHandler commandHandler = new CommandHandler();
		Thread t = new Thread(commandHandler);
		t.start();
		Scanner scanner = new Scanner(System.in);

		while(true) {
			if (!commandHandler.command(scanner.nextLine())) 
				break;
		}
		
		scanner.close();
		controller.removeListener(listener);
		System.exit(1);
	}

}
