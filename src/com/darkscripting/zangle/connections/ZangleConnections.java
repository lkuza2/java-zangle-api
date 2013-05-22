package com.darkscripting.zangle.connections;

import com.darkscripting.zangle.ZangleConnection;
import com.darkscripting.zangle.classes.ZangleClass;
import com.darkscripting.zangle.constants.ZangleConstants;
import com.darkscripting.zangle.exceptions.InvalidUsernamePasswordException;
import com.darkscripting.zangle.object.ZangleObject;
import com.darkscripting.zangle.student.ZStudent;
import org.apache.http.NameValuePair;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the connections and also contains the representation of the ZClass object
 *
 * @author Luke Kuza
 * @version 1.00
 */
public class ZangleConnections extends ZangleObject {

    /**
     * Httpclient variable to access http methods
     */
    protected static DefaultHttpClient httpclient = new DefaultHttpClient();

    /**
     * Static variable of the zangle url to connect to
     */
    protected static String mainzangleurl;

    /**
     * Static variable to see if we are connected to the zangle system.
     */
    protected static boolean connected = false;

    /**
     * Instance of ZangleHttp for get(); and post(); methods
     */
    private ZangleHttp http = ZangleHttp.getInstance();

    /**
     * Username
     */
    private static String username;
    /**
     * Password
     */
    private static String password;


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

        if (zangleurl.endsWith("/")) {
            ZangleConnections.mainzangleurl = zangleurl;
        } else {
            ZangleConnections.mainzangleurl = zangleurl + "/";
        }
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("stuident", ZangleConnections.username));
        list.add(new BasicNameValuePair("stupassword", ZangleConnections.password));
        list.add(new BasicNameValuePair("submit1", "Logon"));
        http.quickGet(ZangleConstants.DEFAULT_EXTENSION, true);
        http.quickPost(ZangleConstants.LOGIN_CHECK_EXTENSION, list, true);
        ArrayList<String> response = http.get(ZangleConstants.STUDENT_SEL_EXTENSION, true);
        int size = response.size();
        int j = 0;
        while (size > 0) {
            if (response.get(j).contains("<b>Your Session Has Timed Out</b>")) {
                throw new Exception("Invalid Username/Password", new InvalidUsernamePasswordException());
            } else if (response.get(j).contains("<form name=\"studentform\" method=\"post\" action=\"stusel.aspx\">")) {
                break;
            }
            size--;
            j++;
        }
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

        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("stuident", ZangleConnections.username));
        list.add(new BasicNameValuePair("stupassword", ZangleConnections.password));
        list.add(new BasicNameValuePair("submit1", "Logon"));
        http.quickGet(ZangleConstants.DEFAULT_EXTENSION, false);
        http.quickPost(ZangleConstants.LOGIN_CHECK_EXTENSION, list, false);
        ArrayList<String> response = http.get(ZangleConstants.STUDENT_SEL_EXTENSION, false);
        int size = response.size();
        int j = 0;
        while (size > 0) {
            if (response.get(j).contains("<b>Your Session Has Timed Out</b>")) {
                throw new Exception("Invalid Username/Password", new InvalidUsernamePasswordException());
            } else if (response.get(j).contains("<form name=\"studentform\" method=\"post\" action=\"stusel.aspx\">")) {
                break;
            }
            size--;
            j++;
        }

    }

    /**
     * Closes the connection, freeing system recourses
     */
    public void close() {
        ZangleConnection.httpclient.getConnectionManager().shutdown();
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
        if (studentPage.get(14).contains(ZangleConstants.SESSION_LOGGED_OUT)) {
            return true;
        } else {
            return false;
        }
    }


}
