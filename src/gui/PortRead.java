package gui;

import com.fazecast.jSerialComm.*;
import java.util.*;
import java.io.*;

public class PortRead 
{
	
	public static void main(String args[]) 
	{
		SerialPort comPort = SerialPort.getCommPorts()[0];
		comPort.openPort();
		//comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
		InputStream in = comPort.getInputStream();
		try
		{
			while(!in.equals(" ")){
				System.out.print((char)in.read());
			}
		   //for (int j = 0; j < 1000; ++j)
		      //System.out.print((char)in.read());
		   in.close();
		} catch (Exception e) { e.printStackTrace(); }
		comPort.closePort();
	}
	
	
	
	
/*
public static SerialPort userPort;
static InputStream in;

public static void main(String args[]) 
{   
    Scanner input = new Scanner(System.in);

    

    SerialPort ports[] = SerialPort.getCommPorts();
    int i = 1;

    //User port selection
    System.out.println("COM Ports available on machine");
    for(SerialPort port : ports) //iterator to pass through port array
    {
        System.out.println(i++ + ": " + port.getSystemPortName()); //print windows com ports
    }
    System.out.println("Please select COM PORT: 'COM#'");
    SerialPort userPort = SerialPort.getCommPort(input.nextLine());

    //Initializing port 
    userPort.openPort();
    if(userPort.isOpen())
    {
        System.out.println("Port initialized!");
        //timeout not needed for event based reading

        //userPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
    }

    else
    {
        System.out.println("Port not available");
        return;
    }   

    userPort.addDataListener(new SerialPortDataListener(){
        @Override
        public int getListeningEvents(){return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;}

        public void serialEvent(SerialPortEvent event)
        {
            if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                return;
            byte[] newData = new byte[userPort.bytesAvailable()];
            int numRead = userPort.readBytes(newData, newData.length);
            for(int i=0; i <newData.length; i++){
            	System.out.println(newData[i]);
            }
            //System.out.println("Read " + numRead + " bytes.");
        }
    });
}*/
}

