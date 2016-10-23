package gui;

import java.io.*;
import java.util.*;

public class Model {
	
	public ArrayList<Integer> temperatureData;
	public ArrayList<Float> airspeedData;
	public ArrayList<Float> lightData;
	public ArrayList<String> timestampData;
	public ArrayList<String> longitudeData;
	public ArrayList<String> latitudeData;

	public Model(){
		//Initialize the ArrayLists
		temperatureData = new ArrayList<Integer>();
		airspeedData = new ArrayList<Float>();
		lightData = new ArrayList<Float>();
		timestampData = new ArrayList<String>();
		longitudeData = new ArrayList<String>();
		latitudeData = new ArrayList<String>();
	}
	
	/*
	 * Get the data in the list
	 */
	public ArrayList<Integer> getTemperatureList(){
		return temperatureData;
	}
	
	/*
	 * Get the data in the list
	 */
	public ArrayList<Float> getAirList(){
		return airspeedData;
	}
	
	/*
	 * Get the data in the list
	 */
	public ArrayList<Float> getLightList(){
		return lightData;
	}
	
	/*
	 * Get the data in the list
	 */
	public ArrayList<String> getTimestampList(){
		return timestampData;
	}
	
	/*
	 * Get the data in the list
	 */
	public ArrayList<String> getLongitudeList(){
		return longitudeData;
	}
	
	/*
	 * Get the data in the list
	 */
	public ArrayList<String> getLatitudeList(){
		return latitudeData;
	}
	
	/*
	 * Read th data from file, and display them
	 */
	@SuppressWarnings("resource")
	public void readSavedFile(String fileName) throws IOException{
		//Clear the buffer 
		temperatureData.clear();
		airspeedData.clear();
		lightData.clear();

		BufferedReader content = new BufferedReader(new FileReader(fileName));
		String line;
		
		while((line = content.readLine()) != null){
			String[] values = line.split(",");
			if(!line.equals("")){
				temperatureData.add(Integer.parseInt(values[0]));
				airspeedData.add(Float.parseFloat(values[1]));
				lightData.add(Float.parseFloat(values[2]));
			}
		}
	}
}
