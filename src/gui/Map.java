package gui;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import java.awt.Dimension;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Map extends JPanel{
    private final JFXPanel jfxPanel;
    private Scene scene;
    private GoogleMapView mapComponent;
    private GoogleMap map;
    private float latitude;
    private float longtitude;

    public Map() {
    	//Initialize the map
    	latitude = (float) -27.50025;
    	longtitude = (float) 153.014523;

        jfxPanel = new JFXPanel();
        jfxPanel.setPreferredSize(new Dimension(600, 600));

        Platform.runLater(() -> {
            mapComponent = new GoogleMapView();
            mapComponent.addMapInializedListener(() -> {

                LatLong center = new LatLong(-27.50025, 153.014523);
                
                //Set some properties of the map
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
                
                //Add markers to the map
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position( new LatLong(latitude, longtitude) )
                            .visible(Boolean.TRUE)
                            .title("My Marker");
  
                Marker marker = new Marker( markerOptions );
                
                
                //Add information window to the marker
                InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                infoWindowOptions.content("<h2>Axon building</h2>"
                                        + "Current Location: -27.50025, 153.014523<br>");
                InfoWindow window = new InfoWindow(infoWindowOptions);
                window.open(map, marker);
                
                map.addMarker(marker);
               

            });

            mapComponent.setPrefSize(600, 600);
            scene = new Scene(mapComponent);

            jfxPanel.setScene(scene);
        });
        this.add(jfxPanel);  
    }
    
    /*
     * Set the position of the marker
     */
    public void setPosition(float la, float lo){
    	latitude = la;
    	longtitude = lo;
    }
}