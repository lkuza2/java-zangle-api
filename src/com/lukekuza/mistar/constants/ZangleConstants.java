package com.lukekuza.mistar.constants;

/**
 * Class contains constants of the Zangle system pages and html pages
 *
 * @author Luke Kuza
 * @version 1.00
 */
public interface ZangleConstants {

    //Parsing Constants

    /**
     * Line to search for if session expired
     */
    final String SESSION_LOGGED_OUT = "Your Session Has Timed Out";

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
     * Login page that must be posted correct login details
     */
    final String LOGIN_CHECK_EXTENSION = "Home/Login";
    /**
     * Student Classes and Assignment page.  Contains ALL assignments
     */
    final String STUDENT_ASSIGN_EXTENSION = "Home/LoadProfileData/Assignments%5Etrue";

}
