package com.darkscripting.zangle.assignment;

import com.darkscripting.zangle.exceptions.InvalidAssignmentIndexException;

/**
 * Class that hold the assignment array for a certain class
 *
 * @author Luke Kuza
 * @version 1.00
 */
public class ZangleAssignment {


    /**
     * Hold the array of all of the students assignments
     */
    private ZAssignment[] assignments;

    /**
     * Returns the ZAssignment object for the specified index. This is returning a certain
     * class's Assignment.
     *
     * @param index Index number
     * @return Returns ZAssignment class
     * @throws Exception Throws exception if assignment/index does not exists
     */
    public ZAssignment getAssignment(int index) throws Exception {
        if (assignments == null || index > assignments.length || index < 0) {
            throw new Exception("Assignment index does not exists", new InvalidAssignmentIndexException());
        } else
            return assignments[index];
    }

    /**
     * Gets the amount of assigments the class has
     *
     * @return Returns int size
     */
    public int getSize() {
        if (assignments == null) {
            return -1;
        } else
            return assignments.length;
    }

    /**
     * Adds an assignment
     *
     * @param zassignment ZAssignment object to add to the array
     * @return Returns a reference to the assignment added
     */
    public ZAssignment addAssignment(ZAssignment zassignment) {
        if (assignments == null) {
            assignments = new ZAssignment[1];
            assignments[0] = zassignment;
        } else {
            // Allocate a new class array and copy the current elements into it.
            ZAssignment[] array = new ZAssignment[assignments.length + 1];
            System.arraycopy(assignments, 0, array, 0, assignments.length);

            // Store the class at the end of the array.
            array[assignments.length] = zassignment;

            // Assign the new class array.
            assignments = array;
        }
        return assignments[assignments.length - 1];
    }

}
