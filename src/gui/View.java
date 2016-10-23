package gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import eu.hansolo.steelseries.gauges.Radial;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class View extends JFrame{
	
	@SuppressWarnings("unused")
	private Model model;
	
	private JTextField air;
	private JTextField temperature;
	private JTextField light;
	private Radial temperatureGauge;
	private Radial airGauge;
	private Radial lightGauge;
	private JButton load;
	private JButton save;
	private JPanel filePane;
	public JPanel stripChartPane;
	public JPanel map;
	private String selectedFileName;
	Map googleMap;

	//Initialize the view class
	public View(Model model) {
		this.model = model;
		setTitle("Meteorological Observation");
		setBounds(150, 150, 900, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container container = getContentPane();
		add_content(container);
	}
	/*
	 * Add the content into a big container
	 */
	public void add_content(Container c) {
		//Add tabbed panel
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
        
        //Display google map
        googleMap = new Map();
        map.add(googleMap);
        
        //Add content into digital panel and analog panel
        add_digital(digitalPane);
        create_temperature_gauge(analogPane);
        create_air_gauge(analogPane);
        create_light_gauge(analogPane);
        add_file(filePane);
        
        c.add(tabbedPane);
	}
	
	
	/*
	 * Add JLabels and JTextfirlds into digital panel
	 */
	public void add_digital(JPanel p){
		JLabel speedLabel = new JLabel("Wind Speed:");
		JLabel temperatureLabel = new JLabel("Temperature:");
		JLabel lightLabel = new JLabel("Light:");
		JLabel temperatureUnitLabel = new JLabel("Degrees Celsius");
		JLabel speedUnitLabel = new JLabel("m/s");
		JLabel lightUnitLabel = new JLabel("Lux (x100)");
		
		air = new JTextField(10);
		air.setEditable(false);
		temperature = new JTextField(10);
		temperature.setEditable(false);
		light = new JTextField(10);
		light.setEditable(false);
	
		 p.add(temperatureLabel);
         p.add(temperature);
         p.add(temperatureUnitLabel);
         p.add(speedLabel);
         p.add(air);
         p.add(speedUnitLabel);
         p.add(lightLabel);
         p.add(light);
         p.add(lightUnitLabel);
	}
	
	/*
	 * Add buttons for loading file and saving file
	 */
	public void add_file(JPanel p){
		load = new JButton("Load data from file");
		save = new JButton("Save data to file");
		p.add(load);
		p.add(save);
	}
	
	/*
	 * Set the value in JTextField
	 */
	public void set_temperature_value(String value){
		temperature.setText(value);
	}
	
	/*
	 * Set the value in JTextField
	 */
	public void set_air_value(String value){
		air.setText(value);
	}
	
	/*
	 * Set the value in JTextField
	 */
	public void set_light_value(String value){
		light.setText(value);
	}
	
	/*
	 * Get the data in the TextField
	 */
	public float get_temperature_value(){
		String result = temperature.getText();
		if(!result.equals("")){
			return Float.parseFloat(result);
		}
		return 0; 
	}
	
	/*
	 * Get the data in the TextField
	 */
	public double get_air_value(){
		String result = air.getText();
		return Double.parseDouble(result);
	}
	
	/*
	 * Get the data in the TextField
	 */
	public double get_light_value(){
		String result = light.getText();
		return Double.parseDouble(result);
	}

	/*
	 * Create a gauge for displaying temperature
	 */
	public void create_temperature_gauge(JPanel p){
        JPanel panel = new JPanel() {
            @Override 
            public Dimension getPreferredSize() {
                return new Dimension(220, 220);
            }
        };
        temperatureGauge = new Radial();
        temperatureGauge.setTitle("Temperature");
        temperatureGauge.setUnitString("Degree Celsius");
        temperatureGauge.setMinValue(-20);
        temperatureGauge.setMaxValue(50);
        
        panel.setLayout(new BorderLayout());
        panel.add(temperatureGauge, BorderLayout.CENTER);
        p.add(panel);
	}
	
	/*
	 * Create a gauge for displaying wind speed
	 */
	public void create_air_gauge(JPanel p){
        JPanel panel = new JPanel() {
            @Override 
            public Dimension getPreferredSize() {
                return new Dimension(240, 240);
            }
        };
        airGauge = new Radial();
        airGauge.setTitle("Wind Speed");
        airGauge.setUnitString("m/s");
        airGauge.setMinValue(0);
        airGauge.setMaxValue(50);
        
        panel.setLayout(new BorderLayout());
        panel.add(airGauge, BorderLayout.CENTER);
        p.add(panel);
	}
	
	/*
	 * Create a gauge for displaying light
	 */
	public void create_light_gauge(JPanel p){
        JPanel panel = new JPanel() {
            @Override 
            public Dimension getPreferredSize() {
                return new Dimension(220, 220);
            }
        };
        lightGauge = new Radial();
        lightGauge.setTitle("Solar Insolation");
        lightGauge.setUnitString("Lux (x100)");
        lightGauge.setMinValue(0);
        lightGauge.setMaxValue(50);

        panel.setLayout(new BorderLayout());
        panel.add(lightGauge, BorderLayout.CENTER);
        p.add(panel);
	}
	
	/*
	 * Set the value in the temperature gauge
	 */
	public void set_temperature_gauge(){
		double value = get_temperature_value();
        temperatureGauge.setValueAnimated(value);
	}
	
	/*
	 * Set the value in the wind speed gauge
	 */
	public void set_air_gauge(){
		double value = get_air_value();
        airGauge.setValueAnimated(value);
	}
	
	/*
	 * Set the value in the light gauge
	 */
	public void set_light_gauge(){
		double value = get_light_value();
        lightGauge.setValueAnimated(value);
	}
	
	/*
	 * Add Action Listener for loading file
	 */
	public void addLoadListener(ActionListener al){
		load.addActionListener(al);
	}
	
	/*
	 * Add Action Listener for saving file
	 */
	public void addSaveListener(ActionListener al){
		save.addActionListener(al);
	}
	
	/*
	 * Open a file chooser when clicking the load button to
	 * let user choose which file to load 
	 */
	public void show_file_chooser(){
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fc.showOpenDialog(filePane);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fc.getSelectedFile();
		    selectedFileName = selectedFile.getName();
		}
	}
	
	/*
	 * Get the file name of the file selected
	 */
	public String get_fileName(){
		return selectedFileName;
	}

	public void setPosition(float la, float lo){
		 googleMap.setPosition(la, lo);
	}

}
