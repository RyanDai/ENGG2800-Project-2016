package gui;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

import java.awt.Dimension;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Map extends JPanel{

    //private final JFrame frame;
    private final JFXPanel jfxPanel;
    private Scene scene;
    private GoogleMapView mapComponent;
    private GoogleMap map;
    private float latitude;
    private float longtitude;

    public Map() {

    	latitude = (float) -27.50025;
    	longtitude = (float) 153.014523;

        jfxPanel = new JFXPanel();
        jfxPanel.setPreferredSize(new Dimension(600, 600));

        Platform.runLater(() -> {
            mapComponent = new GoogleMapView();
            mapComponent.addMapInializedListener(() -> {

                LatLong center = new LatLong(-27.50025, 153.014523);

                MapOptions options = new MapOptions()
                        .center(center)
                        //.mapMarker(true)
                        .zoom(12)
                        .overviewMapControl(false)
                        .panControl(false)
                        .rotateControl(false)
                        .scaleControl(false)
                        .streetViewControl(false)
                        .zoomControl(false)
                        .mapType(MapTypeIdEnum.SATELLITE);

                map = mapComponent.createMap(options);
                
                MarkerOptions markerOptions = new MarkerOptions();

                markerOptions.position( new LatLong(latitude, longtitude) )
                            .visible(Boolean.TRUE)
                            .title("My Marker");
                            

                Marker marker = new Marker( markerOptions );

                map.addMarker(marker);

            });

            mapComponent.setPrefSize(600, 600);
            scene = new Scene(mapComponent);

            jfxPanel.setScene(scene);
        });
        
        

        this.add(jfxPanel);
        
        
        //frame.getContentPane().add(jfxPanel);
        //frame.pack();
        //frame.setVisible(true);
        //frame.setLocationRelativeTo(null);
        
    }
    
    
    public void setPosition(int la, int lo){
    	latitude = la;
    	longtitude = lo;
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