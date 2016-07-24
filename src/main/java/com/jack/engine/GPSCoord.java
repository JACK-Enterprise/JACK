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
    
    public Pos3D toPos3D(double sphereRadius) {
        GPSCoord radians = new GPSCoord();
        
        radians.setLongitude(Math.toRadians(longitude));
        radians.setLatitude(Math.toRadians(latitude / 1.5));
        
        return calculate3DPos(radians, sphereRadius);
    }
    
    private Pos3D calculate3DPos(GPSCoord pos2D, double sphereRadius)
    {
        Pos3D spherePos = new Pos3D();
        double cosOmega = cos(-pos2D.getLatitude());
        double sinOmega = sin(-pos2D.getLatitude());
        double cosTheta = cos(pos2D.getLongitude());
        double sinTheta = sin(pos2D.getLongitude());
        
        spherePos.setZ(sphereRadius * cosOmega * cosTheta);
        spherePos.setX(sphereRadius * cosOmega * sinTheta);
        spherePos.setY(sphereRadius * sinOmega);
        
        return spherePos;
    }
}
