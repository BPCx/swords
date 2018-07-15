package de.meisterfuu.sip.portal.shared;

import com.vaadin.server.Sizeable;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import de.meisterfuu.sip.portal.domain.Address;

/**
 * Created by meisterfuu.
 */
public class MapPanel {
    public static GoogleMap buildMap(Address address, String name) {
        if(address.getLat() != null && address.getLon() != null){
            GoogleMap googleMap = new GoogleMap("AIzaSyB9xPUk0wymHDdiIbXv-ku13Xf6PIc0hEY", null, "english");
            googleMap.setSizeFull();
            googleMap.addStyleName("roundimage");
            googleMap.setHeight(400, Sizeable.Unit.PIXELS);
            googleMap.addMarker(name, new LatLon(
                            Double.valueOf(address.getLat()),
                            Double.valueOf(address.getLon())),
                    false,
                    null);
            googleMap.setMinZoom(8);
            googleMap.setMaxZoom(16);
            googleMap.setZoom(12);
            googleMap.setCenter(new LatLon(
                    Double.valueOf(address.getLat()),
                    Double.valueOf(address.getLon())));
            return googleMap;
        }
        return null;
    }
}
