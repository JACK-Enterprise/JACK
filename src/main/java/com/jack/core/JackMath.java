package com.jack.core;

import com.jack.address.controller.MenuBarController;
import com.jack.engine.EmpriseCoord;
import com.jack.engine.GPSCoord;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * Created by Maxime on 14/04/2016.
 */
public class JackMath {

    private static final double EPSILON1 = 0.1;
    private static final double EPSILON2 = 0.01;
    private static final double EPSILON3 = 0.001;
    private static final double EPSILON4 = 0.0001;
    private static final double EPSILON5 = 0.00001;
    private static final double EPSILON6 = 0.000001;
    private static final double EPSILON7 = 0.0000001;
    private static final double EPSILON8 = 0.00000001;
    private static final double EPSILON9 = 0.000000001;
    private static final double EPSILON10 = 0.0000000001;
    private static final double EPSILON11 = 0.00000000001;
    private static final double EPSILON12 = 0.000000000001;
    private static final double EPSILON13 = 0.0000000000001;
    private static final double EPSILON14 = 0.00000000000001;
    private static final double EPSILON15 = 0.000000000000001;
    private static final double EPSILON16 = 0.0000000000000001;
    private static final double EPSILON17 = 0.00000000000000001;
    private static final double EPSILON18 = 0.000000000000000001;
    private static final double EPSILON19 = 0.0000000000000000001;
    private static final double EPSILON20 = 0.00000000000000000001;
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
        double res = fov > 1? 1 : 0.5;
        double xPos = res * Math.tan(Math.toRadians(35)/ 2) * (Math.abs(zAbs - radius));
        GPSCoord camCoord = new GPSCoord();
        camCoord.setLongitude(-xAngle);
        camCoord.setLatitude((-yAngle * 1.5));
        double minX = camCoord.getLongitude() - xPos * 2;
        double maxX = camCoord.getLongitude() + xPos * 2;

        double minY = camCoord.getLatitude() - xPos * 2;
        double maxY = camCoord.getLatitude() + xPos * 2;
        
        MenuBarController.minLatitude = minX;
        MenuBarController.minLongitude = minY;
        MenuBarController.getInstance().refreshCoordinates();
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

    public static EmpriseCoord[] splitEmprise(EmpriseCoord emprise, int x, int y){
        EmpriseCoord[] tiledEmprise = new EmpriseCoord[x*y];

        GPSCoord min = emprise.getMinCoord();
        GPSCoord max = emprise.getMaxCoord();
        double minLong = min.getLongitude();
        double minLat = min.getLatitude();
        double xCoordSize = max.getLongitude() - minLong;
        double yCoordSize = max.getLatitude() - minLat;

        double xToAdd = xCoordSize / (double)x;
        double yToAdd = yCoordSize / (double)y;

        int i = 0;
        int j = 0;
        int list = 0;

        for (i = 0; i < x; i++){
            double maxLong = minLong + xToAdd;
            minLat = min.getLatitude();
            for(j = 0; j < y; j++){
                double maxLat = minLat + yToAdd;
                tiledEmprise[list] = new EmpriseCoord(minLong, minLat, maxLong, maxLat);
                minLat = maxLat;
                list++;
            }
            minLong = maxLong;
        }

        return tiledEmprise;
    }


}
