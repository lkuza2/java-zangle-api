package com.lukekuza.mistar.connections;

import com.lukekuza.mistar.classes.ZangleClass;
import com.lukekuza.mistar.constants.ZangleConstants;
import com.lukekuza.mistar.exceptions.InvalidUsernamePasswordException;
import com.lukekuza.mistar.object.ZangleObject;
import com.lukekuza.mistar.student.ZStudent;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class handles the connections and also contains the representation of the ZClass object
 *
 * @author Luke Kuza
 * @version 1.00
 */
public class ZangleConnections extends ZangleObject {

    /**
     * Static variable of the zangle url to connect to
     */
    protected static String mainzangleurl;

    /**
     * Static variable to see if we are connected to the zangle system.
     */
    protected static boolean connected = false;
    /**
     * Username
     */
    private static String username;
    /**
     * Password
     */
    private static String password;
    /**
     * Instance of ZangleHttp for get(); and post(); methods
     */
    private ZangleHttp http = ZangleHttp.getInstance();

    /**
     * Connects to specified connection.  If already connected, disconnects current connection.
     *
     * @param zangleurl URL of the HOME zangle page ex. http://example.org/zangle
     * @param username  Username of student to log in as
     * @param password  Password of student
     * @return Returns true if connected, false otherwise
     * @throws Exception Throws exception if username/password is incorrect or all else fails
     */
    public boolean connect(String zangleurl, String username, String password) throws Exception {
        ZangleObject.zstudent = new ZStudent();
        ZangleObject.zclass = new ZangleClass();
        ZangleConnections.username = username;
        ZangleConnections.password = password;

        if (zangleurl.endsWith("/"))
            ZangleConnections.mainzangleurl = zangleurl;
        else
            ZangleConnections.mainzangleurl = zangleurl + "/";

        performLogin(true);

        new ZangleParse().parse();
        connected = true;
        return true;
    }

    /**
     * Reconnects to the currently saved connection.
     *
     * @throws Exception Throws exception if username/password is incorrect or all else fails
     */
    private void reconnect() throws Exception {
        performLogin(false);
    }

    /**
     * Performs actual login of user
     *
     * @throws Exception Throws exception if username/password is incorrect or there is a connectione error
     */
    private void performLogin(boolean firstStart) throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("Pin", ZangleConnections.username);
        params.put("Password", ZangleConnections.password);
        params.put("districtid", "");

        http.quickGet(ZangleConstants.STUDENT_SEL_EXTENSION, firstStart);

        ArrayList<String> response = http.post(ZangleConstants.LOGIN_CHECK_EXTENSION, params, firstStart);
        if (response.size() <= 0 || response.get(0).contains("Invalid"))
            throw new Exception("Invalid Username/Password", new InvalidUsernamePasswordException());
    }

    /**
     * Closes the connection, freeing system recourses
     */
    public void close() {
        connected = false;
    }

    /**
     * Checks if you are connected to zangle
     *
     * @return Returns true if connected, false otherwise
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Representation of the student. Contains methods including Name, Grade, School etc.
     *
     * @return The student
     */
    public ZStudent student() {
        return zstudent;
    }

    /**
     * Updates the cache of the students data.  Call this to refresh all of the information.<br/>
     * Will relogin user if session data is expired
     *
     * @throws Exception Throws exception if there is a problem
     */
    public void update() throws Exception {
        if (isLoggedIn()) {
            new ZangleParse().parse();
        } else {
            reconnect();
            new ZangleParse().parse();
        }
    }

    /**
     * Checks if the currently connected user is logged in with valid session data.<br/>
     * This checks to see if a users session has expired, not if you have connected to the Zangle System
     *
     * @return Returns true if session is valid and user is logged in, returns false otherwise
     * @throws Exception Throws exception if there is an error getting the data
     */
    public boolean isLoggedIn() throws Exception {
        ArrayList<String> studentPage = http.get(ZangleConstants.STUDENT_SEL_EXTENSION, false);
        for (int i = 0; i < studentPage.size(); i++) {
            if (studentPage.get(i).contains(ZangleConstants.SESSION_LOGGED_OUT))
                return true;
        }
        return false;
    }


}
