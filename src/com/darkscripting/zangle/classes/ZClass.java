package com.darkscripting.zangle.classes;

import com.darkscripting.zangle.assignment.ZangleAssignment;
import com.darkscripting.zangle.object.ZangleObject;

/**
 * ZClass object which hold all information for a certain students class
 *
 * @author Luke Kuza
 * @version 1.01
 */
public class ZClass extends ZangleObject {

    /**
     * Name of the students class
     */
    private String name;
    /**
     * Period of the class
     */
    private String period;
    /**
     * Students current grade in the class
     */
    private String grade;
    /**
     * Teachers full name of the class
     */
    private String teacher;
    /**
     * Teachers first name
     */
    private String firstname;
    /**
     * Teachers last name
     */
    private String lastname;
    /**
     * Teacher's Email
     */
    private String email;
    /**
     * Current percent in the class based on number of points
     */
    private double percent;

    /**
     * Value that holds the ZangleAssignment class unique to this class
     */
    private ZangleAssignment assignment = new ZangleAssignment();

    /**
     * ZClass constructer to create a proper ZClass
     *
     * @param name    Name of the class to add
     * @param period  Period of the class
     * @param grade   Current students grade of the class
     * @param teacher Current teacher.  Format: "Bob Example"
     */
    public ZClass(String name, String period, String grade, String teacher, String email) {
        setName(name);
        setPeriod(period);
        setGrade(grade);
        setTeacher(teacher);
        String[] teacherArray = teacher.split(" ");
        setTeacherFirstName(teacherArray[0]);
        setTeacherLastName(teacherArray[1]);
        setEmail(email);
    }

    /**
     * Sets the name of the class
     *
     * @param name Name of the class
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Sets the period of the class
     *
     * @param period to add
     */
    public void setPeriod(String period) {
        this.period = period;
    }

    /**
     * Sets the grade of the class
     *
     * @param grade Grade to set
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * Sets the teacher of the class
     *
     * @param teacher Teacher to set
     */
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    /**
     * Sets the first name of the teacher<br>
     * This method is called when the class is created to automaticly set this
     *
     * @param firstname First name to set
     */
    public void setTeacherFirstName(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Sets the last of the teacher<br>
     * This method is called when the class is created to automaticly set this
     *
     * @param lastname last name to set
     */
    public void setTeacherLastName(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Sets the percentage the student currently has in the class
     *
     * @param percent percent from 0-100
     */
    public void setPercent(double percent) {
        this.percent = percent;
    }

    /**
     * Sets the email for the teacher of the class
     * @param email example@example or null
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * Gets the name of the students class
     *
     * @return Returns name in string
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the period of the class
     *
     * @return Returns the period
     */
    public String getPeriod() {
        return period;
    }

    /**
     * Gets the grade for the class
     *
     * @return Returns the grade. Returns null if there is no grade entry.
     */
    public String getGrade() {
        return grade;
    }

    /**
     * Gets the teacher's full name for the class
     *
     * @return Returns the teacher's full name
     */
    public String getTeacher() {
        return teacher;
    }

    /**
     * Gets the teacher's first name
     *
     * @return Returns the teacher's first name
     */
    public String getTeacherFirstName() {
        return firstname;
    }

    /**
     * Gets the teacher's last name
     *
     * @return Returns the teacher's last name
     */
    public String getTeacherLastName() {
        return lastname;
    }

    /**
     * Gets the percent in the class
     *
     * @return Returns a double of the current percentage score in the class
     */
    public double getPercent() {
        return percent;
    }

    /**
     * Gets the email of the teacher of the class, if supported.
     * @return Returns the email, example@example.com or null if feature not supported.
     */
    public String getEmail(){
        return email;
    }

    /**
     * Gets the assignments for this specific class
     *
     * @return Returns the ZangleAssignment class for this specific class.<br>
     *         This class contains furthur methods
     */
    public ZangleAssignment getAssignments() {
        return assignment;
    }


}
