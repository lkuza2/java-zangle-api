package com.lukekuza.mistar.classes;

import com.lukekuza.mistar.exceptions.InvalidClassIndexException;

/**
 * ZangleClass class. Hold array and function for returning the ZClass object
 *
 * @author Luke Kuza
 * @version 1.00
 */
public class ZangleClass {


    /**
     * Hold the array of all of the students classes
     */
    private ZClass[] classes;

    /**
     * Returns the ZClass object for the specified index. This is returning a certain
     * students class.
     *
     * @param index Index number
     * @return Returns ZClass class
     * @throws Exception Throws exception if class/index does not exists
     */
    public ZClass getClass(int index) throws Exception {
        if (classes == null || index > classes.length || index < 0) {
            throw new Exception("Class index does not exists", new InvalidClassIndexException());
        } else
            return classes[index];
    }

    public ZClass[] getClasses() {
        return classes;
    }

    /**
     * Gets the amount of classes a student has
     *
     * @return Returns int size
     */
    public int getSize() {
        return classes.length;
    }

    /**
     * Adds a class
     *
     * @param zclass ZClass object to add to the array
     */
    public void addClass(ZClass zclass) {
        if (classes == null) {
            classes = new ZClass[1];
            classes[0] = zclass;
        } else {
            // Allocate a new class array and copy the current elements into it.
            ZClass[] array = new ZClass[classes.length + 1];
            System.arraycopy(classes, 0, array, 0, classes.length);

            // Store the class at the end of the array.
            array[classes.length] = zclass;

            // Assign the new class array.
            classes = array;
        }
    }

}
