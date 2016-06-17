package com.jack.engine;

import static java.lang.Math.PI;
import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Aurelien
 */
@AllArgsConstructor
@NoArgsConstructor
public class GPSCoord {
    @Getter @Setter private double longitude;
    @Getter @Setter private double latitude;
    
    public double getSphericalDistance(GPSCoord coord, double radius) {
        double lg1 = Math.toRadians(longitude);
        double lg2 = Math.toRadians(coord.getLongitude());
        double lat1 = Math.toRadians(latitude);
        double lat2 = Math.toRadians(coord.getLatitude());
        double kmPerDegrees = calculateCirconference(radius) / 360;
        return kmPerDegrees * Math.toDegrees(acos(sin(lat1) * sin(lat2) + cos(lat1) * cos(lat2) * cos(lg2 - lg1)));
    }
    
    public double calculateCirconference(double radius)
    {
        return 2 * PI * radius;
    }
}
