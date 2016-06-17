package com.jack.core;

import static com.jack.core.StdDevLib.*;
import static com.jack.core.JackMath.*;

/**
 * Created by Maxime on 18/05/2016.
 */
public class Rectangle {

    private double north;
    private double south;
    private double west;
    private double east;

    public Rectangle(double west, double south, double east, double north){

        this.west = defaultValue(west, 0.0);
        this.east = defaultValue(east, 0.0);
        this.north = defaultValue(north, 0.0);
        this.south = defaultValue(south, 0.0);
    }

    public double getNorth() {
        return north;
    }

    public double getSouth() {
        return south;
    }

    public double getWest() {
        return west;
    }

    public double getEast() {
        return east;
    }

    public double computeWidth(){
        double east = this.east;
        double west = this.west;

        if(east < west)
            east += TWO_PI;

        return east - west;
    }

    public double computeWidth(Rectangle rectangle){
        double east = rectangle.east;
        double west = rectangle.west;

        if(east < west)
            east += TWO_PI;

        return east - west;
    }

    public double computeHeight(){
        return north - south;
    }

    public double computeHeight(Rectangle rectangle){
        return rectangle.north - rectangle.south;
    }
}
