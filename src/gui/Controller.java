package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.fazecast.jSerialComm.*;

import javax.swing.JFileChooser;

import org.jfree.util.StringUtils;

public class Controller {

	private Model model;
	private View view;
	private ArrayList<Float> temperatureData;
	private ArrayList<Float> airspeedData;
	private ArrayList<Float> lightData;
	private ArrayList<String> timestampData;
	private ArrayList<String> longitudeData;
	private ArrayList<String> latitudeData;
	private int count;
	//private char[] received;
	public int temperature;
	public int windSpeed;
	public int light;
	public DTSCTest stripChart;

	public Controller(Model model, View view) throws IOException {
		this.model = model;
		model.readFile("data.txt");
		temperatureData = model.getTemperatureList();
		/*for(int i = 0; i < temperatureData.size(); i++){
			System.out.println(temperatureData.get(i));
		}*/
		this.view = view;
		temperatureData = model.getTemperatureList();
		airspeedData = model.getAirList();
		lightData = model.getLightList();
		timestampData = model.getTimestampList();
		longitudeData = model.getLongitudeList();
		latitudeData = model.getLatitudeList();
		count = 0;
		view.addLoadListener(new LoadActionListener());
		view.addSaveListener(new SaveActionListener());
		
		
		
		
		class Multi implements Runnable{
			
			public void run(){
				SerialPort comPort = SerialPort.getCommPorts()[0];
				comPort.openPort();
				//comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
				InputStream in = comPort.getInputStream();
				try
				{
					while(!in.equals(" ")){
						char[] received = new char[2048];
						ArrayList<Integer> receivedData = new ArrayList<Integer>();
						//System.out.print((char)in.read());
						
						for(int i = 0; i < 1000; i++){
							char data = (char)in.read();
							if(data != '\n'){
								received[i] = data;
							}else{
								break;
							}
							
						}
						/*for(int i = 0; i < 20; i++){
							System.out.print(received[i]);
						}*/
						String receivedStr = "";
						for(int i = 0; i < received.length; i++){
							receivedStr += received[i];
						}
						
						receivedStr = receivedStr.replace("\n", "").replace("\r", "");
						
						//receivedStr.replaceAll("\\s+","");
						System.out.println(receivedStr);
						String[] splitStr = receivedStr.split(" ");
						
						for(int i = 0; i < splitStr.length; i++){
							splitStr[i] = splitStr[i].trim();
							if(splitStr[i].matches("\\d+")){
								//System.out.println(splitStr[i]);
								receivedData.add(Integer.parseInt(splitStr[i]));
							}
						}
						temperature = receivedData.get(0);
						windSpeed = receivedData.get(1);
						light = receivedData.get(2);
					}
				   
				   in.close();
				} catch (Exception e) { e.printStackTrace(); }
				comPort.closePort();
			}
		}
		
		Multi m1 = new Multi();
		Thread t1 = new Thread(m1);
		t1.start();
		stripChart = new DTSCTest("Weather station");
		view.stripChartPane.add(stripChart);
		start();
	}
	
	 
	public void start() {
	    final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	    
	    service.scheduleWithFixedDelay(new Runnable()
	      {
	        @Override
	        public void run()
	        {
	        	/*
	        	view.set_temperature_value(temperatureData.get(count).toString());
	        	view.set_air_value(airspeedData.get(count).toString());
	        	view.set_light_value(lightData.get(count).toString());
	        	view.set_timestamp_value(timestampData.get(count));
	        	view.set_longitude_value(longitudeData.get(count));
	        	view.set_latitude_value(latitudeData.get(count));
	     
	        	view.set_temperature_gauge();
	        	view.set_air_gauge();
	        	view.set_light_gauge();
	        	//System.out.println(timestampData);
	        	count++;
	        	//view.create_strip_chart();
	        	*/
	        	view.set_temperature_value(String.valueOf(temperature));
	        	view.set_air_value(String.valueOf(windSpeed));
	        	view.set_light_value(String.valueOf(light));
	        	
	        	view.set_temperature_gauge();
	        	view.set_air_gauge();
	        	view.set_light_gauge();
	        	
	        	
	        	stripChart.set_data(temperature, windSpeed, light);
	            stripChart.start();
	            //view.stripChartPane.add(stripChart);
	        }
	      }, 0, 1, TimeUnit.SECONDS);
	  }
	
	private class LoadActionListener implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		try {
    			view.show_file_chooser();
				model.readSavedFile(view.get_fileName());
				temperatureData = model.getTemperatureList();
				airspeedData = model.getAirList();
				lightData = model.getLightList();
				timestampData = model.getTimestampList();
				longitudeData = model.getLongitudeList();
				latitudeData = model.getLatitudeList();
				count = 0;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
	}
	
	private class SaveActionListener implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		try {
				FileWriter output = new FileWriter("testoutput.txt");
				String ls = System.getProperty("line.separator");
				for(int i = 0; i < 10; i++){
					output.write(timestampData.get(i) + ",");
					output.write(airspeedData.get(i) + ",");
					output.write(temperatureData.get(i) + ",");
					output.write(lightData.get(i) + ",");
					output.write(longitudeData.get(i) + ",");
					output.write(latitudeData.get(i) + ls);
				}
				output.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
	}
	
	
}
