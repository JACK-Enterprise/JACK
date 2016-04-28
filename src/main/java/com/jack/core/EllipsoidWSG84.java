package com.jack.core;

/**
 * Instance an Ellipsoid defined by the WSG84 standard
 * Created by Maxime on 25/04/2016.
 */
public class EllipsoidWSG84 extends Ellipsoid {

    public EllipsoidWSG84(){
        super(6378137.0, 6378137.0, 6356752.3142451793);
    }
}
