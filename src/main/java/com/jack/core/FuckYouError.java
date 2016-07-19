package com.jack.core;

/**
 * Created by maxim on 19/07/2016.
 */
public class FuckYouError extends Exception{
    private String name;
    private String message;

    /**
     * Constructs an object that is thrown due to a bullshit error like you don't read the fucking manual,
     * missing parameters or argument out of range...
     *
     * @version 1.0
     * @param message The message that indicates what kind of error is thrown
     */
    public FuckYouError(String message){

        this.name = "Fuck You Error";
        this.message = message;
    }

    public String toString(){
        String str = this.name + ": " + this.message;

        return str;
    }
}
