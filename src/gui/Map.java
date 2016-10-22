package gui;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import java.awt.Dimension;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Map extends JPanel{

    private final JFrame frame;
    private final JFXPanel jfxPanel;
    private Scene scene;
    private GoogleMapView mapComponent;
    private GoogleMap map;

    public Map() {

        frame = new JFrame("Hello Swing GMapsFX");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jfxPanel = new JFXPanel();
        jfxPanel.setPreferredSize(new Dimension(600, 600));

        Platform.runLater(() -> {
            mapComponent = new GoogleMapView();
            mapComponent.addMapInializedListener(() -> {

                LatLong center = new LatLong(-27.5, 153.014114);

                MapOptions options = new MapOptions()
                        .center(center)
                        .mapMarker(true)
                        .zoom(12)
                        .overviewMapControl(false)
                        .panControl(false)
                        .rotateControl(false)
                        .scaleControl(false)
                        .streetViewControl(false)
                        .zoomControl(false)
                        .mapType(MapTypeIdEnum.SATELLITE);

                map = mapComponent.createMap(options);

            });

            mapComponent.setPrefSize(600, 600);
            scene = new Scene(mapComponent);

            jfxPanel.setScene(scene);
        });

        this.add(jfxPanel);
        /*frame.getContentPane().add(jfxPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
         */
    }

    /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new Map();
        });
    }*/

}