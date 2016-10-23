package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import com.fazecast.jSerialComm.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/*
 * The controller class control model and view.
 * */
public class Controller {

	private Model model;
	private View view;
	private ArrayList<Integer> temperatureData;
	private ArrayList<Float> airspeedData;
	private ArrayList<Float> lightData;
	private ArrayList<String> longitudeData;
	private ArrayList<String> latitudeData;
	private int count;
	public int temperature;
	public float windSpeed;
	public float light;
	public float latitude;
	public float longtitude;
	public DTSCTest stripChart;
	private int mode;

	public Controller(Model model, View view) throws IOException {
		this.model = model;
		String port;
		//Open a pop-up window to ask user for port number
		JFrame frame = new JFrame("Select COM port");
		while(true){
			port = 
		            JOptionPane.showInputDialog(frame, "Enter the port number as COM#:");
			break;
		}
		this.view = view;
		temperatureData = new ArrayList<Integer>();
		airspeedData = new ArrayList<Float>();
		lightData = new ArrayList<Float>();
		longitudeData = model.getLongitudeList();
		latitudeData = model.getLatitudeList();
		count = 0;
		//initialize the mode to 1: serial communication.
		//mode 2 is loading file
		mode = 1;
		
		view.addLoadListener(new LoadActionListener());
		view.addSaveListener(new SaveActionListener());

		class Multi implements Runnable{
			//Use this thread to read data from the port
			public void run(){
				SerialPort comPort = SerialPort.getCommPort(port);
				comPort.openPort();
				InputStream in = comPort.getInputStream();
				try
				{
					while(!in.equals(" ")){
						char[] received = new char[2048];
						ArrayList<Integer> receivedData = new ArrayList<Integer>();
						
						for(int i = 0; i < 10000; i++){
							char data = (char)in.read();
							if(data != '\n'){
								received[i] = data;
							}else{
								break;
							}
						}
						//Save the data into a string.
						String receivedStr = "";
						for(int i = 0; i < received.length; i++){
							receivedStr += received[i];
						}
						receivedStr = receivedStr.trim();
						receivedStr = receivedStr.replace("\n", "").replace("\r", "");
						System.out.println(receivedStr);

						if(receivedStr.length() < 70 && receivedStr.length() > 55){
							System.out.println(receivedStr.length());
							String[] splitStr = receivedStr.split(",");
							
							for(int i = 0; i < splitStr.length; i++){
								if(isNumber(splitStr[i])){
									receivedData.add(Integer.parseInt(splitStr[i]));
								}
							}
							//Save the data into the variables
							temperature = receivedData.get(0);
							windSpeed = ((float)receivedData.get(1))/10;
							light = ((float)receivedData.get(2))/100;
							latitude = ((float) receivedData.get(3))/100;
							latitude = ((float) receivedData.get(4))/100;
							temperatureData.add(temperature);
							airspeedData.add(windSpeed);
							lightData.add(light);
							
							System.out.println(temperature + "  "  + windSpeed + "  "  + light);
						}
					}
				   
				   in.close();
				} catch (Exception e) { e.printStackTrace(); }
				comPort.closePort();
			}
		}
		
		Multi m1 = new Multi();
		Thread t1 = new Thread(m1);
		t1.start();
		//Start the strip chart
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
	        	//Serial communication
	        	if(mode == 1){
	        		view.set_temperature_value(String.valueOf(temperature));
		        	view.set_air_value(String.valueOf(windSpeed));
		        	view.set_light_value(String.valueOf(light));
		        	
		        	view.set_temperature_gauge();
		        	view.set_air_gauge();
		        	view.set_light_gauge();
		        	
		        	view.setPosition(latitude, longtitude);
		        	        	
		        	stripChart.set_data(temperature, windSpeed, light);
		            stripChart.start();
	        	}
	        	//Loading data from file
	        	if(mode == 2){
	        		view.set_temperature_value(temperatureData.get(count).toString());
		        	view.set_air_value(airspeedData.get(count).toString());
		        	view.set_light_value(lightData.get(count).toString());
		        	
		        	view.set_temperature_gauge();
		        	view.set_air_gauge();
		        	view.set_light_gauge();
		        	
		        	temperature = temperatureData.get(count);
		        	windSpeed = airspeedData.get(count);
		        	light = lightData.get(count);

		    		stripChart.set_data(temperature, windSpeed, light);
		            stripChart.start();
		        	
		        	count++;
	        	}
	        }
	      }, 0, 200, TimeUnit.MILLISECONDS);
	  }
	/*
	 * Add Action listener to Load button.
	 * Load data from chosen file
	 */
	private class LoadActionListener implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		try {
    			view.show_file_chooser();
				model.readSavedFile(view.get_fileName());
				temperatureData = model.getTemperatureList();
				airspeedData = model.getAirList();
				lightData = model.getLightList();
				mode = 2;
				count = 0;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
	}
	
	
	private class SaveActionListener implements ActionListener{
		/*
		 * Add Action listener to Save button.
		 * Save data to a file
		 */
    	public void actionPerformed(ActionEvent e){
    		try {
				FileWriter output = new FileWriter("output.csv");
				String ls = System.getProperty("line.separator");
				//Write the content into file
				for(int i = 0; i < temperatureData.size(); i++){
					output.write(temperatureData.get(i) + ",");
					output.write(airspeedData.get(i) + ",");
					output.write(lightData.get(i) + "");
					output.write(ls);
				}
				output.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
	}
	/*
	public float get_temperature(){
		return (float)temperature;
	}
	
	public float get_windSpeed(){
		return (float)windSpeed;
	}
	
	public float get_light(){
		return (float)light;
	}*/
	
	/**
	 * 
	 * @param str the string need to check
	 * @return if a string is numeric
	 */
	public boolean isNumber(String str){
		try{
			int result = Integer.parseInt(str);
		} catch (NumberFormatException e){
			return false;
		}
		return true;
	}
	
}
