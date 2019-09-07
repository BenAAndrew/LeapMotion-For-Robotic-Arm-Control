package main;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import gnu.io.*;

public class SerialHandler {
	final int TIMEOUT = 2000;
	final int BAUDRATE = 9600;
	final static char NEWLINE = '\n';
	static OutputStream arduino;
	
	
	public SerialHandler() {
		Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier port = portEnum.nextElement();
            if(port.getPortType() == CommPortIdentifier.PORT_SERIAL) {
            	try {
					SerialPort serialPort = (SerialPort) port.open(this.getClass().getName(),TIMEOUT);
			        serialPort.setSerialPortParams(BAUDRATE,
									                SerialPort.DATABITS_8,
									                SerialPort.STOPBITS_1,
									                SerialPort.PARITY_NONE);
			        arduino = serialPort.getOutputStream();
					System.out.println("Connected to "+port.getName());
					break;
				} catch (PortInUseException e) {
					System.out.println(port.getName()+" in use");
				} catch (UnsupportedCommOperationException e) {
					System.out.println("Incorrect config for "+port.getName());
				} catch (IOException e) {
					System.out.println("Can't write to "+port.getName());
				}
            }
        }        
	}
	
	public void sendCommand(String command) {
		System.out.println(command);
		try {
            arduino.write((command+NEWLINE).getBytes());
            //arduino.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void sendCode(String code) {
		try {
            arduino.write((code+NEWLINE).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}