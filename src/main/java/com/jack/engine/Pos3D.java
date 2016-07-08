package com.jack.engine;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
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
public class Pos3D {
    @Getter @Setter private double x;
    @Getter @Setter private double y;
    @Getter @Setter private double z;
    
    public double getAlgebricDistance(Pos3D dest) {
        return sqrt(pow(x - dest.getX(), 2) + pow(y - dest.getY(), 2) + pow(z - dest.getZ(), 2));
    }
    
    public double getManhattanDistance(Pos3D dest) {
        return abs(x - dest.getX()) + abs(y - dest.getY()) + abs(z - dest.getZ());
    }
    
    public GPSCoord toGPSCoord() {
        GPSCoord gpsPos = new GPSCoord();
        double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
        double theta = Math.atan(x / z);
        double phi = Math.asin(-y / r);
        
        gpsPos.setLongitude(Math.toDegrees(theta));
        gpsPos.setLatitude(Math.toDegrees(phi) * 1.5);
        
        return gpsPos;
    }
}
