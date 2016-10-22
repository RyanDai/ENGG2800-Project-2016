package gui;


import java.awt.BorderLayout;
import java.awt.EventQueue;
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
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;



public class DTSCTest extends JPanel {
    private static final String TITLE = "Weather Station";
    private static final String START = "Start";
    private static final String STOP = "Stop";
    private static final float MINMAX = 100;
    private static final int COUNT = 100;
    private static final int FAST10 = 40;
    private static final int FAST5 = 200;
    private static final int REALTIME = FAST5 * 5;
    private static final Random random = new Random();
    private Timer timer;
    private int count;
    public float[] data;
    

    public DTSCTest(final String title) {
        Model model = new Model();
        View view = new View(model);
        //Controller controller = new Controller(model, view);
        Date date = new Date();
        count = 0;
        final DynamicTimeSeriesCollection dataset =
            new DynamicTimeSeriesCollection(3, COUNT, new Second());
        dataset.setTimeBase(new Second(date));
        //0, 0, 0, 1, 1, 2011
        dataset.addSeries(gaussianData(), 0, "Temperature");
        dataset.addSeries(gaussianData2(), 1, "Air Speed");
        dataset.addSeries(gaussianData3(), 2, "Light");
        JFreeChart chart = createChart(dataset);
        data = new float[3];

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
            		/*if(model.getTemperatureList().size() != 0){
            			newData[0] = model.getTemperatureList().get(i);
            		} else {
            			newData[0] = 0;
            		}*/
            		/*newData[0] = model.getTemperatureList().get(count);
                    newData[1] = model.getAirList().get(count);
                    newData[2] = model.getLightList().get(count);*/
	            	//newData[0] = view.get_temperature_value();
	                //newData[1] = (float) view.get_light_value();
	                //newData[2] = (float) view.get_air_value();
            		newData[0] = data[0];
            		newData[1] = data[1];
            		newData[2] = data[2];
                    dataset.advanceTime();
                    dataset.appendData(newData);
                    count++;
                    //System.out.println(date.toString());
                
            }
            
            
        });
    }
    
    
    public void set_data(float a, float b, float c){
    	data[0] = a;
        data[1] = b;
        data[2] = c;
    }

    

    private float[] gaussianData() {
        float[] a = new float[COUNT];
        for (int i = 0; i < a.length; i++) {
            a[i] = 0;
        }
        return a;
    }
    
    

    private float[] gaussianData2() {
        float[] a = new float[COUNT];
        for (int i = 0; i < a.length; i++) {
            a[i] = 0;
        }
        return a;
    }
    


    private float[] gaussianData3() {
        float[] a = new float[COUNT];
        for (int i = 0; i < a.length; i++) {
            a[i] = 0;
        }
        return a;
    }

    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
            TITLE, "hh:mm:ss", "Value", dataset, true, true, false);
        final XYPlot plot = result.getXYPlot();
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();
        range.setRange(-MINMAX, MINMAX);
        return result;
    }

    public void start() {
        timer.start();
    }

    /*public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                DTSCTest demo = new DTSCTest(TITLE);
                demo.pack();
                RefineryUtilities.centerFrameOnScreen(demo);
                demo.setVisible(true);
                demo.start();
            }
        });
    }*/
}