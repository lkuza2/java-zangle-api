package com.lukekuza.mistar;

import com.lukekuza.mistar.connections.ZangleConnections;

/**
 * Connects/creates the ZangleConnection.  This class must be called.
 *
 * @author Luke Kuza
 * @version 1.00
 */
final public class ZangleConnection extends ZangleConnections {

    /**
     * Constructor for a ZangleConnection. <br>
     * You must call connect(); to connect to the Zangle system
     */
    public ZangleConnection() {

    }

    /**
     * Constructor for a ZangleConnection. <br>
     * Creates a connection with the given params
     *
     * @param zangleurl URL of the HOME Zangle page ex. http://example.org/zangle
     * @param username  Username of student to log in as
     * @param password  Password of student
     * @throws Exception Throws exception if there was a problem
     */
    public ZangleConnection(String zangleurl, String username, String password) throws Exception {
        connect(zangleurl, username, password);
    }

}
