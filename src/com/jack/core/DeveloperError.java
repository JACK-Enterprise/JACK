package com.jack.core;

/**
 * Created by Maxime on 14/04/2016.
 */
public class DeveloperError extends Exception{
    private String name;
    private String message;

    /**
     * Constructs an object that is thrown due to a developer error like invalid argument,
     * missing parameters or argument out of range...
     *
     * @version 1.0
     * @param message The message that indicates what kind of error is thrown
     */
    public DeveloperError(String message){

        this.name = "DeveloperError";
        this.message = message;
    }

    public String toString(){
        String str = this.name + ": " + this.message;

        return str;
    }
}