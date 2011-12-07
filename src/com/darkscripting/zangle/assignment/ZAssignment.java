package com.darkscripting.zangle.assignment;

import com.darkscripting.zangle.object.ZangleObject;

/**
 * Class that hold assignment details such as name, duedate, etc...
 *
 * @author Luke Kuza
 * @version 1.00
 */
public class ZAssignment extends ZangleObject {

    /**
     * Due date of the assignment
     */
    private String duedate;
    /**
     * Assignment name of the assignment
     */
    private String assignmentname;
    /**
     * Points possible of the assignment
     */
    private String points;
    /**
     * Students score on the assignment
     */
    private String score;
    /**
     * The assignments detail
     */
    private String details;
    /**
     * Students percent on this assignment
     */
    private double percent;
    /**
     * Boolean denoting whether the assignment is extra credit
     */
    private boolean extracredit;
    /**
     * Boolean denoting whether the assignment is not graded
     */
    private boolean notgraded;

    /**
     * ZAssignment constructer to create a proper ZAssignment class
     *
     * @param duedate        Due date of the assignment to add.  Could be anything. <br>
     *                       Optimal format: ex. 12/16/10
     * @param assignmentname Name of the assignment
     * @param points         Total points the assignment is worth
     * @param score          The students score on the assignment<br>
     *                       Pass nothing or null if there is no score yet
     * @param details        Details of the assignment, if any
     * @param extracredit    True if this assignment is extra credit, false otherwise
     * @param notgraded      True if this assignment is extra credit, false otherwise
     */
    public ZAssignment(String duedate, String assignmentname, String points, String score, String details, boolean extracredit, boolean notgraded) {
        setDueDate(duedate);
        setAssignmentName(assignmentname);
        setPoints(points);
        setScore(score);
        setDetails(details);
        setExtraCredit(extracredit);
        setNotGraded(notgraded);
    }

    /**
     * Sets the due date for the assignment
     *
     * @param duedate The due date to set<br>
     *                Optimal format: ex. 12/16/10
     */
    public void setDueDate(String duedate) {
        this.duedate = duedate;
    }

    /**
     * Set the name of the assignment
     *
     * @param assignmentname Name of the assignment
     */
    public void setAssignmentName(String assignmentname) {
        this.assignmentname = assignmentname;
    }

    /**
     * Set the total amount of points the assignment is worth
     *
     * @param points Amount of points the assignment is worth
     */
    public void setPoints(String points) {
        this.points = points;
    }

    /**
     * Sets the student score of the assignment
     *
     * @param score The score the student received on the assignment
     */
    public void setScore(String score) {
        this.score = score;
    }

    /**
     * Sets the assignment details
     *
     * @param details The details of the assignment
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * Sets the percent for this assignment.
     *
     * @param percent Double percent. ex 12.23
     */
    public void setPercent(double percent) {
        this.percent = percent;
    }

    /**
     * Sets the extra credit flag for this assignment.
     *
     * @param extracredit Pass true if extra credit, false otherwise
     */
    public void setExtraCredit(boolean extracredit) {
        this.extracredit = extracredit;
    }

    /**
     * Sets the not graded flag for this assignemnt
     *
     * @param notgraded Pass true if not graded, false otherwise
     */
    public void setNotGraded(boolean notgraded) {
        this.notgraded = notgraded;
    }


    /**
     * Gets the due date of the assignment
     *
     * @return Returns the due date
     */
    public String getDueDate() {
        return duedate;
    }

    /**
     * Gets the name of the assignment
     *
     * @return Returns the name of the assignment
     */
    public String getAssignmentName() {
        return assignmentname;
    }

    /**
     * Gets the total amount of points the assignment is worth
     *
     * @return Returns the total amount of points the assingment is worth<br/>
     *         Returns "0" if there is no points that this assignment is out of
     */
    public String getPoints() {
        if (points.isEmpty()) {
            return "0";
        } else {
            return points;
        }
    }

    /**
     * Gets the student's score on the assignment
     *
     * @return Returns the student's score on the assignment<br>
     *         Returns a blank string or "0" if there is no score.
     */
    public String getScore() {
        if (score.isEmpty()) {
            return "0";
        } else {
            return score;
        }
    }

    /**
     * Gets the assignment details of this assignment
     *
     * @return Returns the assignments details.  Every new line is denoted by \n</br>
     *         Returns blank if there is no details
     */
    public String getDetails() {
        return details;
    }

    /**
     * Gets the student's percent on the assignment
     *
     * @return Returns the students percent in double format, ex. 89.43<br/>
     *         If assignment is extra credit, will return 00.00.<br/>
     *         If assignment is not graded, will return the percent regardless.
     */
    public double getPercent() {
        return percent;
    }

    /**
     * Returns boolean if assignment is extra credit.
     *
     * @return True if extra credit, false otherwise
     */
    public boolean isExtraCredit() {
        return extracredit;
    }

    /**
     * Returns boolean if assignment is not graded
     *
     * @return True if not graded, false otherwise
     */
    public boolean isNotGraded() {
        return notgraded;
    }


}
