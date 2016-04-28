package com.jack.core;

/**
 * Created by Maxime on 14/04/2016.
 */
public class StdDevLib {
    /**
     * Returns the first parameter if defined, else the second.
     * Useful to set a default value for a parameter
     *
     * @param a
     *          Any object or var to check if defined
     * @param b
     *          The default value to use if the param a is undefined, must be the same type as a
     * @return Returns the first parameter if defined, else the second.
     *
     * @version 1.0
     */
    public static <T> T defaultValue(T a, T b){
        if(a != null){
            return a;
        }
        return b;
    }

    /**
     * Returns true if the object is defined otherwise, returns false
     * Useful to check if we can work on an object
     *
     * @param value The object or var
     * @param <T> All type of object or var
     * @return Returns true if the object is defined, returns false if the object is undefined
     * @version 1.0
     */
    public static <T> Boolean defined(T value){
        if(value != null)
            return true;

        return false;
    }

}
