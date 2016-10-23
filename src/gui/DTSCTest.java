package gui;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;
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



public class DTSCTest extends JPanel {
    private static final String TITLE = "Weather Station";
    private static final String START = "Start";
    private static final String STOP = "Stop";
    private static final int COUNT = 30;
    private static final int FAST10 = 40;
    private static final int FAST5 = 200;
    private static final int REALTIME = FAST5 * 5;
    private Timer timer;
    private int count;
    public float[] data;
    

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public DTSCTest(final String title) {
        Model model = new Model();
        View view = new View(model);
        Date date = new Date();
        count = 0;
        //Add data series to the chart
        final DynamicTimeSeriesCollection dataset =
            new DynamicTimeSeriesCollection(3, COUNT, new Second());
        dataset.setTimeBase(new Second(date));
        //0, 0, 0, 1, 1, 2011
        dataset.addSeries(gaussianData(), 0, "Temperature (Degrees Celsius)");
        dataset.addSeries(gaussianData2(), 1, "Air Speed (m/s)");
        dataset.addSeries(gaussianData3(), 2, "Light (Lux) (x100)");
        JFreeChart chart = createChart(dataset);
        data = new float[3];
        
        //Add buttons
        final JButton run = new JButton(STOP);
        run.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();
                if (STOP.equals(cmd)) {
                    timer.stop();
                    run.setText(START);
                } else {
                    timer.start();
                    run.setText(STOP);
                }
            }
        });

        final JComboBox combo = new JComboBox();
        combo.addItem("Real-time");
        combo.addItem("5X speed");
        combo.addItem("10X speed");
        combo.addActionListener(new ActionListener() {

            @Override
            /*
             * Change speed of the chart 
             * 
             */
            public void actionPerformed(ActionEvent e) {
                if ("5X speed".equals(combo.getSelectedItem())) {
                    timer.setDelay(FAST5);
                } else if("10X speed".equals(combo.getSelectedItem())){
                    timer.setDelay(FAST10);
                } else {
                	timer.setDelay(REALTIME);
                }
            }
        });

        this.add(new ChartPanel(chart), BorderLayout.CENTER);
        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(run);
        btnPanel.add(combo);
        this.add(btnPanel, BorderLayout.SOUTH);
        

        timer = new Timer(REALTIME, new ActionListener() {

            float[] newData = new float[3];
           
            @Override
            public void actionPerformed(ActionEvent e) {
            		newData[0] = data[0];
            		newData[1] = data[1];
            		newData[2] = data[2];
                    dataset.advanceTime();
                    dataset.appendData(newData);
                    count++;
            }
        });
    }
    
    /*
     * @para a b c:the data received
     * set the dataset to be the data read from serial
     * */
    public void set_data(float a, float b, float c){
    	data[0] = a;
        data[1] = b;
        data[2] = c;
    }

    
    /*
     * Initialize the dataset before reading data in
     */
    private float[] gaussianData() {
        float[] a = new float[COUNT];
        for (int i = 0; i < a.length; i++) {
            a[i] = 0;
        }
        return a;
    }
    
    /*
     * Initialize the dataset before reading data in
     */
    private float[] gaussianData2() {
        float[] a = new float[COUNT];
        for (int i = 0; i < a.length; i++) {
            a[i] = 0;
        }
        return a;
    }
    
    /*
     * Initialize the dataset before reading data in
     */
    private float[] gaussianData3() {
        float[] a = new float[COUNT];
        for (int i = 0; i < a.length; i++) {
            a[i] = 0;
        }
        return a;
    }
    
    /*
     * Create chart with X axis and Y axis
     */
    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
            TITLE, "hh:mm:ss", "Value", dataset, true, true, false);
        final XYPlot plot = result.getXYPlot();
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();
        range.setRange(-20, 50);
        range.setLabel("Degrees Celsius, m/s, Lux(x100)");
        
        return result;
    }

    /*
     * Start the timer of the chart
     * */
    public void start() {
        timer.start();
    }

}