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
     * Line to search for when the line contains a description
     */
    final String ASSIGNMENT_DETAILS_PAGE = "<td valign=\"top\"";

    /**
     * Line to search when it is the end of the details page
     */
    final String END_OF_DETAILS_PAGE = "</table>";

    /**
     * Line to search and remove when the details have a title
     */
    final String ASSIGNMENT_DETAILS_TITLE = "<td valign=\"top\" nowrap align=\"right\"><b>";

    /**
     * Line to search for if session expired
     */
    final String SESSION_LOGGED_OUT = "history.forward();";

    //Extensions constants

    /**
     * Student sel page  Contains student information.
     */
    final String STUDENT_SEL_EXTENSION = "Home/PortalMainPage";
    /**
     * Student selection page, to select student
     */
    final String STUDENT_SELECTION_EXTENSION = "StudentBanner/SetStudentBanner/";
    /**
     * Default page that must be called to get the cookie
     */
    final String DEFAULT_EXTENSION = "default.aspx";
    /**
     * Login page that must be posted correct login details
     */
    final String LOGIN_CHECK_EXTENSION = "Home/Login";
    /**
     * Student Classes and Assignment page.  Contains ALL assignments
     */
    final String STUDENT_ASSIGN_EXTENSION = "Home/LoadProfileData/Assignments%5Etrue";
    /**
     * Assignments page.  Contains the assignment description(if any)
     */
    final String ASSIGNMENT_DESCRIPTION_EXTENSION = "stuassignmentdetail.aspx?t=";

}
