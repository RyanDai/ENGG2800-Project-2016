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
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * An example to show how we can create a dynamic chart.
*/
public class LiveGraphDemo extends JPanel implements ActionListener {

    /** The time series data. */
    private TimeSeries series;

    /** The most recent value added. */
    private double lastValue = 100.0;

    /** Timer to refresh graph after every 1/4th of a second */
    private Timer timer = new Timer(1000, this);

    /**
     * Constructs a new dynamic chart application.
     *
     * @param title  the frame title.
     */
    public LiveGraphDemo() {

        
        this.series = new TimeSeries("Random Data", Second.class);

        final TimeSeriesCollection dataset = new TimeSeriesCollection(this.series);
        final JFreeChart chart = createChart(dataset);

        //timer.setInitialDelay(1000);
        /*
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
        
        */
        this.add(new ChartPanel(chart), BorderLayout.CENTER);
        timer.start();

    }

    /**
     * Creates a sample chart.
     *
     * @param dataset  the dataset.
     *
     * @return A sample chart.
     */
    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
            "Dynamic Line And TimeSeries Chart",
            "Time",
            "Value",
            dataset,
            true,
            true,
            false
        );

        final XYPlot plot = result.getXYPlot();

        plot.setBackgroundPaint(new Color(0xffffe0));
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.lightGray);

        ValueAxis xaxis = plot.getDomainAxis();
        xaxis.setAutoRange(true);

        //Domain axis would show data of 60 seconds for a time
        xaxis.setFixedAutoRange(60000.0);  // 60 seconds
        xaxis.setVerticalTickLabels(true);

        ValueAxis yaxis = plot.getRangeAxis();
        yaxis.setRange(0.0, 300.0);

        return result;
    }
    /**
     * Generates an random entry for a particular call made by time for every 1/4th of a second.
     *
     * @param e  the action event.
     */
    public void actionPerformed(final ActionEvent e) {

        final double factor = 0.9 + 0.2*Math.random();
        this.lastValue = this.lastValue * factor;

        final Second now = new Second();
        this.series.add(new Second(), this.lastValue);
    }

    /**
     * Starting point for the dynamic graph application.
     *
     * @param args  ignored.
     */
    /*public static void main(final String[] args) {

        final LiveGraphDemo demo = new LiveGraphDemo();
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }*/
}