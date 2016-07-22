package com.jack.core;

import com.jack.engine.EmpriseCoord;
import com.jack.engine.GPSCoord;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * Created by Maxime on 14/04/2016.
 */
public class JackMath {

    private static double EPSILON1 = 0.1;
    private static double EPSILON2 = 0.01;
    private static double EPSILON3 = 0.001;
    private static double EPSILON4 = 0.0001;
    private static double EPSILON5 = 0.00001;
    private static double EPSILON6 = 0.000001;
    private static double EPSILON7 = 0.0000001;
    private static double EPSILON8 = 0.00000001;
    private static double EPSILON9 = 0.000000001;
    private static double EPSILON10 = 0.0000000001;
    private static double EPSILON11 = 0.00000000001;
    private static double EPSILON12 = 0.000000000001;
    private static double EPSILON13 = 0.0000000000001;
    private static double EPSILON14 = 0.00000000000001;
    private static double EPSILON15 = 0.000000000000001;
    private static double EPSILON16 = 0.0000000000000001;
    private static double EPSILON17 = 0.00000000000000001;
    private static double EPSILON18 = 0.000000000000000001;
    private static double EPSILON19 = 0.0000000000000000001;
    private static double EPSILON20 = 0.00000000000000000001;
    public static  double TWO_PI = 2.0 * Math.PI;

    public static double getEPSILON1() {
        return EPSILON1;
    }

    public static double getEPSILON2() {
        return EPSILON2;
    }

    public static double getEPSILON3() {
        return EPSILON3;
    }

    public static double getEPSILON4() {
        return EPSILON4;
    }

    public static double getEPSILON5() {
        return EPSILON5;
    }

    public static double getEPSILON6() {
        return EPSILON6;
    }

    public static double getEPSILON7() {
        return EPSILON7;
    }

    public static double getEPSILON8() {
        return EPSILON8;
    }

    public static double getEPSILON9() {
        return EPSILON9;
    }

    public static double getEPSILON10() {
        return EPSILON10;
    }

    public static double getEPSILON11() {
        return EPSILON11;
    }

    public static double getEPSILON12() {
        return EPSILON12;
    }

    public static double getEPSILON13() {
        return EPSILON13;
    }

    public static double getEPSILON14() {
        return EPSILON14;
    }

    public static double getEPSILON15() {
        return EPSILON15;
    }

    public static double getEPSILON16() {
        return EPSILON16;
    }

    public static double getEPSILON17() {
        return EPSILON17;
    }

    public static double getEPSILON18() {
        return EPSILON18;
    }

    public static double getEPSILON19() {
        return EPSILON19;
    }

    public static double getEPSILON20() {
        return EPSILON20;
    }

    /**
     *
     * @param x
     * @param z
     * @param fov
     * @param radius
     * @param xAngle
     * @param yAngle
     * @return EmpriseCoord
     */
    public static EmpriseCoord xPosbyFov (double x, double z, double fov, double radius, double xAngle, double yAngle) {

        double zAbs = Math.abs(z);
        double xPos = Math.tan(Math.toRadians(fov)/ 2) * (Math.abs(zAbs - radius));
        GPSCoord camCoord = new GPSCoord();
        camCoord.setLongitude(-xAngle);
        camCoord.setLatitude((-yAngle * 1.5));


        double minX = camCoord.getLongitude() - xPos * 10;
        double maxX = camCoord.getLongitude() + xPos * 10;

        double minY = camCoord.getLatitude() - xPos * 10;
        double maxY = camCoord.getLatitude() + xPos * 10;

        double camPos = camCoord.getLongitude();

        /*
        System.out.println("Z " + z + " X : " + x);
        System.out.println("Radius : " + radius);
        System.out.println("X Cam : " + camPos + " || xMaxPos : " + maxX);
        System.out.println("Emprise : " + minX + " " + minY + " " + maxX + " " + minY);
        */

        EmpriseCoord emprise = new EmpriseCoord(minX, minY, maxX, maxY);
        return emprise;
    }



}
