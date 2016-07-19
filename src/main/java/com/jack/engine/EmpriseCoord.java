package com.jack.engine;

/**
 * Created by maxim on 19/07/2016.
 */
public class EmpriseCoord {
    private GPSCoord minCoord;
    private GPSCoord maxCoord;

    /**
     * Constructor
     */
    public EmpriseCoord(){
    }

    public EmpriseCoord(double minLongitude, double minLatitude, double maxLongitude, double maxLatitude){
        minCoord = new GPSCoord();
        maxCoord = new GPSCoord();
        
        minCoord.setLongitude(minLongitude);
        minCoord.setLatitude(minLatitude);
        
        maxCoord.setLongitude(maxLongitude);
        maxCoord.setLatitude(maxLatitude);
        
    }
    
    public EmpriseCoord(GPSCoord minCoord, GPSCoord maxCoord){
        this.minCoord = minCoord;
        this.maxCoord = maxCoord;
       
    }

    public GPSCoord getMinCoord() {
        return minCoord;
    }

    public void setMinCoord(GPSCoord minCoord) {
        this.minCoord = minCoord;
    }

    public GPSCoord getMaxCoord() {
        return maxCoord;
    }

    public void setMaxCoord(GPSCoord maxCoord) {
        this.maxCoord = maxCoord;
    }
}
 