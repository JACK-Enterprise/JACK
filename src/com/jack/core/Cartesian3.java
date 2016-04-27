package com.jack.core;

import static com.jack.core.StdDevLib.*;
/**
 * Created by Maxime on 14/04/2016.
 */
public class Cartesian3 {

    public double x;
    public double y;
    public double z;

    /**
     *
     * @param x
     *          The x component.
     * @param y
     *          The y component.
     * @param z
     *          The z component.
     */
    public Cartesian3(double x, double y, double z){

        this.x = defaultValue(x, 0.0);
        this.y = defaultValue(y, 0.0);
        this.z = defaultValue(z, 0.0);
    }

}
