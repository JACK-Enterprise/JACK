package com.jack.core;

import static com.jack.core.StdDevLib.*;
import static com.jack.core.JackMath.*;

/**
 * Created by Maxime on 14/04/2016.
 */
public class Ellipsoid {

    private Cartesian3 radii;
    private Cartesian3 radiiSquared;
    private Cartesian3 radiiToTheFourth;
    private Cartesian3 oneOverRadii;
    private Cartesian3 getOneOverRadiiSquared;
    private double minimumRadius;
    private double maximumRadius;
    private double centerToleranceSquared;

    public Ellipsoid(double x, double y, double z){
        try {
            initialize(this, x, y, z);
        }
        catch (DeveloperError e){
            e.printStackTrace();
        }
    }

    private void initialize( Ellipsoid ellipsoid, double x, double y, double z) throws DeveloperError {
        x = defaultValue(x, 0.0);
        y = defaultValue(y, 0.0);
        z = defaultValue(z, 0.0);

        if (x < 0.0 || y < 0.0 || z < 0.0)
            throw new DeveloperError("All radii components must be greater or equal to 0.0");

        ellipsoid.radii = new Cartesian3(x, y, z);
        ellipsoid.radiiSquared = new Cartesian3(x * x, y * y, z * z);
        ellipsoid.radiiToTheFourth = new Cartesian3(x * x * x *x,
                                                y * y * y * y,
                                                z * z * z * z);
        ellipsoid.oneOverRadii = new Cartesian3(x == 0.0 ? 0.0 : 1.0 / x,
                                            y == 0.0 ? 0.0 : 1.0 / y,
                                            z == 0.0 ? 0.0 : 1.0 / z);

        ellipsoid.getOneOverRadiiSquared = new Cartesian3(x == 0.0 ? 0.0 : 1.0 / (x * x),
                                                    y == 0.0 ? 0.0 : 1.0 / (y * y),
                                                    z == 0.0 ? 0.0 : 1.0 / (z * z));

        ellipsoid.minimumRadius = Math.min(x, y);
        ellipsoid.minimumRadius = Math.min(this.minimumRadius, z);

        ellipsoid.maximumRadius = Math.max(x, y);
        ellipsoid.maximumRadius = Math.max(this.maximumRadius, z);

        ellipsoid.centerToleranceSquared = getEPSILON1();

    }

    public Cartesian3 getRadii() {
        return radii;
    }

    public Cartesian3 getRadiiSquared() {
        return radiiSquared;
    }

    public Cartesian3 getRadiiToTheFourth() {
        return radiiToTheFourth;
    }

    public Cartesian3 getOneOverRadii() {
        return oneOverRadii;
    }

    public Cartesian3 getGetOneOverRadiiSquared() {
        return getOneOverRadiiSquared;
    }

    public double getMinimumRadius() {
        return minimumRadius;
    }

    public double getMaximumRadius() {
        return maximumRadius;
    }

    public double getCenterToleranceSquared() {
        return centerToleranceSquared;
    }
}