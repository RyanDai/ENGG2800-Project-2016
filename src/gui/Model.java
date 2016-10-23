package gui;

import java.io.*;
import java.util.*;

public class Model {
	
	public ArrayList<Integer> temperatureData;
	public ArrayList<Integer> airspeedData;
	public ArrayList<Integer> lightData;
	public ArrayList<String> timestampData;
	public ArrayList<String> longitudeData;
	public ArrayList<String> latitudeData;

	public Model(){
		temperatureData = new ArrayList<Integer>();
		airspeedData = new ArrayList<Integer>();
		lightData = new ArrayList<Integer>();
		timestampData = new ArrayList<String>();
		longitudeData = new ArrayList<String>();
		latitudeData = new ArrayList<String>();
		/*try {
			readFile("data.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
	
	
	/*public void readFile(String fileName) throws IOException{
		BufferedReader content = new BufferedReader(new FileReader(fileName));
		String line;
		
		line = content.readLine();
		String[] values = line.split(",");
		for(int i = 0; i < values.length; i++){
			float value = Float.parseFloat(values[i]);
			temperatureData.add(value);
		}
		line = content.readLine();
		String[] values2 = line.split(",");
		for(int i = 0; i < values2.length; i++){
			float value2 = Float.parseFloat(values2[i]);
			airspeedData.add(value2);
		}
		line = content.readLine();
		String[] values3 = line.split(",");
		for(int i = 0; i < values3.length; i++){
			float value3 = Float.parseFloat(values3[i]);
			lightData.add(value3);
		}
		line = content.readLine();
		String[] values4 = line.split(",");
		for(int i = 0; i < values4.length; i++){
			timestampData.add(values4[i]);
		}
		line = content.readLine();
		String[] values5 = line.split(",");
		for(int i = 0; i < values5.length; i++){
			longitudeData.add(values5[i]);
		}
		line = content.readLine();
		String[] values6 = line.split(",");
		for(int i = 0; i < values6.length; i++){
			latitudeData.add(values6[i]);
		}
		content.close();
	}
	*/
	public ArrayList<Integer> getTemperatureList(){
		return temperatureData;
	}
	
	public ArrayList<Integer> getAirList(){
		return airspeedData;
	}
	
	public ArrayList<Integer> getLightList(){
		return lightData;
	}
	
	public ArrayList<String> getTimestampList(){
		return timestampData;
	}
	
	public ArrayList<String> getLongitudeList(){
		return longitudeData;
	}
	
	public ArrayList<String> getLatitudeList(){
		return latitudeData;
	}
	
	public void readSavedFile(String fileName) throws IOException{
		temperatureData.clear();
		airspeedData.clear();
		lightData.clear();

		
		BufferedReader content = new BufferedReader(new FileReader(fileName));
		String line;
		
		while((line = content.readLine()) != null){
			//System.out.println(line);
			String[] values = line.split(",");
			if(!line.equals("")){
				temperatureData.add(Integer.parseInt(values[0]));
				airspeedData.add(Integer.parseInt(values[1]));
				lightData.add(Integer.parseInt(values[2]));
			}
			
			
		}
		
		
	}
	
	
}
