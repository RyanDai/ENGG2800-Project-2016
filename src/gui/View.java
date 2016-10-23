package gui;

import java.awt.*;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import eu.hansolo.steelseries.gauges.Radial;
import eu.hansolo.steelseries.gauges.RadialBargraph;

import org.pushingpixels.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Second;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class View extends JFrame{
	
	private Model model;
	
	private JTextField air;
	private JTextField temperature;
	private JTextField light;
	private JTextField timeStamp;
	private JTextField latitude;
	private JTextField longitude;
	private Radial temperatureGauge;
	private Radial airGauge;
	private Radial lightGauge;
	private JButton load;
	private JButton save;
	private JPanel filePane;
	public JPanel stripChartPane;
	public JPanel map;
	private String selectedFileName;
	private DTSCTest chart;

	
	
	public View(Model model) {
		this.model = model;
		setTitle("Meteorological Observation");
		setBounds(150, 150, 900, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container container = getContentPane();
		add_content(container);
		//add_stripchart();
		//create_strip_chart();
		
	}
	
	public void add_content(Container c) {
		JTabbedPane tabbedPane = new JTabbedPane();
		filePane = new JPanel();
		JPanel digitalPane = new JPanel();
		digitalPane.setLayout(new GridLayout(3,3));
		JPanel analogPane = new JPanel();
		stripChartPane = new JPanel();
		map = new JPanel();
        tabbedPane.addTab("Digital", digitalPane);
        tabbedPane.addTab("Analog", analogPane);
        tabbedPane.addTab("stripChartPane", stripChartPane);
        tabbedPane.add("File", filePane);
        tabbedPane.addTab("GPS Map", map);
        
        //DTSCTest stripChart = new DTSCTest("Weather station");
        //stripChart.start();
        //stripChartPane.add(stripChart);
        //LiveGraphDemo t = new LiveGraphDemo();
        //stripChartPane.add(t);
        Map googleMap = new Map();
        map.add(googleMap);
        
        add_digital(digitalPane);
        create_temperature_gauge(analogPane);
        create_air_gauge(analogPane);
        create_light_gauge(analogPane);
        add_file(filePane);
        
        
        c.add(tabbedPane);
	}
	
	
	
	public void add_digital(JPanel p){
		JLabel l2 = new JLabel("Wind Speed:");
		JLabel l1 = new JLabel("Temperature:");
		JLabel l3 = new JLabel("Light:");
		JLabel l7 = new JLabel("Degrees Celsius");
		JLabel l8 = new JLabel("m/s");
		JLabel l9 = new JLabel("Lux");
		

		air = new JTextField(10);
		air.setEditable(false);
		temperature = new JTextField(10);
		temperature.setEditable(false);
		light = new JTextField(10);
		light.setEditable(false);
	
		 p.add(l1);
         p.add(temperature);
         p.add(l7);
         p.add(l2);
         p.add(air);
         p.add(l8);
         p.add(l3);
         p.add(light);
         p.add(l9);

	}
	
	public void add_file(JPanel p){
		load = new JButton("Load data from file");
		save = new JButton("Save data to file");
		p.add(load);
		p.add(save);
		
	}
	
	public void set_temperature_value(String value){
		temperature.setText(value);
	}
	
	public void set_air_value(String value){
		air.setText(value);
	}
	
	public void set_light_value(String value){
		light.setText(value);
	}
	
	public void set_timestamp_value(String value){
		timeStamp.setText(value);
	}
	
	public void set_longitude_value(String value){
		longitude.setText(value);
	}
	
	public void set_latitude_value(String value){
		latitude.setText(value);
	}
	
	public float get_temperature_value(){
		String result = temperature.getText();
		if(!result.equals("")){
			return Float.parseFloat(result);
		}
		return 0; 
	}
	
	public double get_air_value(){
		String result = air.getText();
		return Double.parseDouble(result);
	}
	
	public double get_light_value(){
		String result = light.getText();
		return Double.parseDouble(result);
	}

	
	public void create_temperature_gauge(JPanel p){
        JPanel panel = new JPanel() {
            @Override 
            public Dimension getPreferredSize() {
                return new Dimension(200, 200);
            }
        };
        temperatureGauge = new Radial();
        temperatureGauge.setTitle("Temperature");
        temperatureGauge.setUnitString("Celsius");
        temperatureGauge.setMinValue(-20);
        temperatureGauge.setMaxValue(99999);
        
        
        panel.setLayout(new BorderLayout());
        panel.add(temperatureGauge, BorderLayout.CENTER);
        p.add(panel);
	}
	
	public void create_air_gauge(JPanel p){
        JPanel panel = new JPanel() {
            @Override 
            public Dimension getPreferredSize() {
                return new Dimension(250, 250);
            }
        };
        airGauge = new Radial();
        airGauge.setTitle("Air Speed");
        airGauge.setUnitString("km/h");
        airGauge.setMinValue(0);
        airGauge.setMaxValue(99999);
        
        
        panel.setLayout(new BorderLayout());
        panel.add(airGauge, BorderLayout.CENTER);
        p.add(panel);
	}
	
	public void create_light_gauge(JPanel p){
        JPanel panel = new JPanel() {
            @Override 
            public Dimension getPreferredSize() {
                return new Dimension(200, 200);
            }
        };
        lightGauge = new Radial();
        lightGauge.setTitle("Solar Insolation");
        lightGauge.setUnitString("kW/m^2");
        lightGauge.setMinValue(0);
        lightGauge.setMaxValue(99999);
        
        
        panel.setLayout(new BorderLayout());
        panel.add(lightGauge, BorderLayout.CENTER);
        p.add(panel);
	}
	
	public void set_temperature_gauge(){
		double value = get_temperature_value();
        temperatureGauge.setValueAnimated(value);
	}
	
	public void set_air_gauge(){
		double value = get_air_value();
        airGauge.setValueAnimated(value);
	}
	
	public void set_light_gauge(){
		double value = get_light_value();
        lightGauge.setValueAnimated(value);
	}
	
	public void addLoadListener(ActionListener al){
		load.addActionListener(al);
	}
	
	public void addSaveListener(ActionListener al){
		save.addActionListener(al);
	}
	
	public void show_file_chooser(){
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fc.showOpenDialog(filePane);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fc.getSelectedFile();
		    selectedFileName = selectedFile.getName();
		}
	}
	
	public String get_fileName(){
		return selectedFileName;
	}

	

}
