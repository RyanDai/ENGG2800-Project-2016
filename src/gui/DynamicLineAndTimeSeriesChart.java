package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

public class DynamicLineAndTimeSeriesChart implements ActionListener{
	/** The time series data. */
	private TimeSeries series;

	/** The most recent value added. */
	private double lastValue = 100.0;

	/** Timer to refresh graph after every 1/4th of a second */
	private Timer timer = new Timer(0, null);//1/4th = 250 ms, to put 1 minute then = 1000*60. 1 second = 1000 ms, 1/4 secon = 250 ms
	private double lastValue2 = 1.0;
	/**
	 * Constructs a new dynamic chart application.
	 *
	 * @param title  the frame title.
	 */
	public DynamicLineAndTimeSeriesChart(final String title) {

	  
	    this.series = new TimeSeries("Random Data", Millisecond.class);

	    final TimeSeriesCollection dataset = new TimeSeriesCollection(this.series);

	    final JFreeChart chart = createChart(dataset, dataset);

	    timer.setInitialDelay(1000);

	    //Sets background color of chart
	    chart.setBackgroundPaint(Color.LIGHT_GRAY);

	    //Created JPanel to show graph on screen
	    final JPanel content = new JPanel(new BorderLayout());

	    //Created Chartpanel for chart area
	    final ChartPanel chartPanel = new ChartPanel(chart);

	    //Added chartpanel to main panel
	    content.add(chartPanel);

	    //Sets the size of whole window (JPanel)
	    chartPanel.setPreferredSize(new java.awt.Dimension(800, 500));

	    //Puts the whole content on a Frame
	    //setContentPane(content);

	    timer.start();

	}

	/**
	 * Creates a sample chart.
	 *
	 * @param dataset  the dataset.
	 *
	 * @return A sample chart.
	 */
	private JFreeChart createChart(final XYDataset dataset, final XYDataset dataset2) 
	{
	    final JFreeChart result = ChartFactory.createTimeSeriesChart("Dynamic Line And TimeSeries Chart",
	        "Time (every 2 minutes)",
	        "Value of binary bits",
	        dataset,
	        true,
	        true,
	        false
	    );
//	        
//	        final JFreeChart result2 = ChartFactory.createTimeSeriesChart("Dynamic Line And TimeSeries Chart",
//	            "Time (every 2 minutes)",
//	            "Value of binary bits",
//	            dataset,
//	            true,
//	            true,
//	            false
	 //        );
//	        


	    final XYPlot plot = result.getXYPlot();
//	        final XYPlot plot2 = result2.getXYPlot();

//	        plot2.setBackgroundPaint(Color.BLACK);
//	        plot2.setDomainGridlinesVisible(true);
//	        

	    plot.setBackgroundPaint(new Color(0xffffe0));
	    plot.setDomainGridlinesVisible(true);
	    plot.setDomainGridlinePaint(Color.lightGray);
	    plot.setRangeGridlinesVisible(true);
	    plot.setRangeGridlinePaint(Color.lightGray);

	    ValueAxis xaxis = plot.getDomainAxis();

	    xaxis.setAutoRange(true);

	    //Domain axis would show data of 60 seconds for a time
	    xaxis.setFixedAutoRange((1*60.0*1000.0));  // 60 seconds = 1 minutes, 60*60.0*1000.0= 1 hour of graph
	    xaxis.setVerticalTickLabels(true);

	    ValueAxis yaxis = plot.getRangeAxis();
	    yaxis.setRange(0.0, 300.0);

//	        ValueAxis xaxis2 = plot2.getDomainAxis();

//	        xaxis2.setAutoRange(true);
	//
//	        //Domain axis would show data of 60 seconds for a time
//	        xaxis2.setFixedAutoRange((1*60.0*1000.0));  // 60 seconds = 1 minutes, 60*60.0*1000.0= 1 hour of graph
//	        xaxis2.setVerticalTickLabels(true);

	    ValueAxis yaxis2 = plot.getRangeAxis();
	    yaxis2.setRange(0.0, 1000.0);

	    return result;
	}

	public void actionPerformed(final ActionEvent e) {

	    final double factor = 0.9 + 0.2*Math.random();
	    this.lastValue = this.lastValue * factor;

	    this.lastValue2 = Math.random();
	   // final Millisecond now = new Millisecond();
	    this.series.add(new Millisecond(), this.lastValue);

	   // System.out.println("Current Time in Milliseconds = " + now.toString()+", Current Value : "+this.lastValue);
	}
	
}
