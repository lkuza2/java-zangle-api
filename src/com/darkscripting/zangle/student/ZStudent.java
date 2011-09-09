package com.darkscripting.zangle.student;

import com.darkscripting.zangle.classes.ZangleClass;
import com.darkscripting.zangle.object.ZangleObject;

/**
 * Class which represents the student who is connected.  Contains methods for information of the student.
 *
 * @author Luke Kuza
 * @version 1.00
 */
public class ZStudent extends ZangleObject {

    /**
     * Full name of the student
     */
    private String name;
    /**
     * School that the student attends
     */
    private String school;
    /**
     * Current grade of the student
     */
    private String grade;

    /**
     * Sets the full name of student.<br>
     * Format: "Bob Example"
     *
     * @param name Fulle name of the student
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the school of the student
     *
     * @param school Name of the school to set
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     * Sets the grade of the student
     *
     * @param grade The grade to set
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * Gets the full name of the student
     *
     * @return returns the full name of the student
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the first name of the student
     *
     * @return Returns the first name of the student
     */
    public String getFirstName() {
        String[] first = name.split(" ");
        return first[0];
    }

    /**
     * Gets the last name of the student
     *
     * @return Returns the last name of the student
     */
    public String getLastName() {
        String[] last = name.split(" ");
        return last[1];
    }

    /**
     * Gets the school the student attends
     *
     * @return Returns the school the student attends
     */
    public String getSchool() {
        return school;
    }

    /**
     * Gets the current grade of the student
     *
     * @return Returns the grade of the student
     */
    public String getGrade() {
        return grade;
    }

    /**
     * Gets the classes of the student.
     *
     * @return Returns the classes of the student.
     */
    public ZangleClass getClasses() {
        return zclass;
    }


}
