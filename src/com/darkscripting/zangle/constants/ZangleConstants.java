package com.darkscripting.zangle.constants;

/**
 * Class contains constants of the Zangle system pages and html pages
 *
 * @author Luke Kuza
 * @version 1.00
 */
public interface ZangleConstants {

    //Parsing Constants

    /**
     * Line to search for when finding students name
     */
    final String USERNAME_HTML_PARSE = "<td align=\"left\" valign=\"middle\">";
    /**
     * Line to search for when finding users school
     */
    final String SCHOOL_HTML_PARSE = "Membership:";
    /**
     * Line to search for when finding the students grade
     */
    final String GRADE_HTML_PARSE = "<td align=\"center\" valign=\"middle\" bgcolor=\"#6666CC\" class=\"stubanner\">";
    /**
     * Line to search for when finding the students period and all other class information
     */
    final String CLASSES_HTML_PARSE = "Period:";
    //final String CLASS_HTML_PARSE = "&nbsp;&nbsp;";

    /**
     * Line to search for when the end of the assignment.aspx file is reached and we may finish parsing
     */
    final String END_OF_CLASSES_HTML_PARSE = "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">";

    /**
     * Line to search for when we are finding a new assignment
     */
    final String ASSIGNMENT_HTML_PARSE = "<input type=\"hidden\"";

    /**
     * Line to search for to find if assignment is extra credit/not graded
     */
    final String ASSIGNMENT_EXTRA_CREDIT_NOT_GRADED_PARSE = "<img src=\"images/check3.gif\" height=\"15\" border=\"0\">";

    /**
     * Line to search to find if assignment has details  Line 34
     */
    final String ASSIGNMENT_DETAILS = "title=\"Show Assignment Detail\"></a>";

    /**
     * Line to search for if session expired
     */
    final String SESSION_LOGGED_OUT = "history.forward();";

    //Extensions constants

    /**
     * Student sel page  Contains student information.
     */
    final String STUDENT_SEL_EXTENSION = "stusel.aspx";
    /**
     * Default page that must be called to get the cookie
     */
    final String DEFAULT_EXTENSION = "default.aspx";
    /**
     * Login page that must be posted correct login details
     */
    final String LOGIN_CHECK_EXTENSION = "logincheck.aspx";
    /**
     * Student demographics page.  Contains Student information
     */
    final String STUDENT_DEM_EXTENSION = "studemographics.aspx";
    /**
     * Student Classes and Assignment page.  Contains ALL assignments
     */
    final String STUDENT_ASSIGN_EXTENSION = "stuassignments.aspx?AssP=L";
    /**
     * Student Classes and Assignments page. Contains recent assignments
     */
    final String STUDENT_RECENTASSIGN_EXTENSION = "stuassignments.aspx";

}
