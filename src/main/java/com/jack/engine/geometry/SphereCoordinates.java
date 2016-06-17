/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jack.engine.geometry;

import com.jack.engine.GPSCoord;
import com.jack.engine.Pos3D;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Aurelien
 */
@AllArgsConstructor
public class SphereCoordinates {
    
    @Setter @Getter private GPSCoord pos;
    
    public Pos3D get3DPosUsingDegrees(double sphereRadius) {
        GPSCoord radians = new GPSCoord();
        
        radians.setLongitude(Math.toRadians(pos.getLongitude()));
        radians.setLatitude(Math.toRadians(pos.getLatitude() / 1.5));
        
        return calculate3DPos(radians, sphereRadius);
    }
    
    private Pos3D calculate3DPos(GPSCoord pos2D, double sphereRadius)
    {
        Pos3D spherePos = new Pos3D();
        double powRadius = sphereRadius * sphereRadius;
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
