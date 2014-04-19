package com.lukekuza.mistar.response;

import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * Class for handling responses from an http request
 *
 * @author Luke Kuza
 * @version 1.00
 */
public class ZangleResponse {

    /**
     * Processes BufferedReader and entity to add the data to an array list
     *
     * @param br BufferedReader of a connection
     * @return Returns an array list of all of the data of BufferedReader stream
     * @throws Exception Throws exception if there is a problem
     */
    protected ArrayList<String> response(BufferedReader br) throws Exception {
        ArrayList<String> list = new ArrayList<String>();
        String str;

        while ((str = br.readLine()) != null) {
            list.add(str);
        }

        br.close();
        return list;
    }


}
