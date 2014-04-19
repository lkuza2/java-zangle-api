package com.darkscripting.zangle.connections;

import com.darkscripting.zangle.constants.ZangleConstants;
import com.darkscripting.zangle.exceptions.NotConnectedException;
import com.darkscripting.zangle.response.ZangleResponse;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Contains methods for getting http pages and posting to http pages and getting the response
 *
 * @author Luke Kuza
 * @version 1.00
 */
public class ZangleHttp extends ZangleResponse {

    /**
     * Instance variable for singleton
     */
    private static ZangleHttp instance;
    private List<String> cookies = new ArrayList<String>();
    private CookieManager cookieManager = new CookieManager();

    /**
     * Constructor
     */
    private ZangleHttp() {

    }

    /**
     * Gets the instance of ZangleHttp
     *
     * @return Returns the instance of ZangleHttp
     */
    public static ZangleHttp getInstance() {
        if (instance == null) {
            instance = new ZangleHttp();
        }
        return instance;
    }

    /**
     * Method to post to the zangle url
     *
     * @param extension    Extension of url, or a page, like stusel.aspx
     * @param data         List of data being posted
     * @param firstconnect If connecting for first time, omits not connected exception if set at true
     * @return Returns ArrayList of the response of the post
     * @throws Exception Thrown if connection can not be made
     */
    protected ArrayList<String> post(String extension, HashMap<String, String> data, boolean firstconnect) throws Exception {
        if (ZangleConnections.connected == false) {
            if (firstconnect != true) {
                throw new Exception("You are not connected!", new NotConnectedException());
            }
        }

        URLConnection con = new URL(ZangleConnections.mainzangleurl + extension).openConnection();
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        // String cookie = getCookies();

        //con.setRequestProperty("Cookie", cookie);
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0");
        cookieManager.setCookies(con);
        con.connect();

        byte[] byteData = getRequestParamString(data).getBytes("UTF-8");

        OutputStream outputStream = con.getOutputStream();

        outputStream.write(byteData);
        outputStream.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

        cookieManager.storeCookies(con);
        return response(br);
    }

    /**
     * Gets HTTP data from zangle url
     *
     * @param extension    Extension of page, ex. stusel.aspx
     * @param firstconnect If connecting for first time, omits not connected exception if set at true
     * @return Returns ArrayList of the response of the post
     * @throws Exception Throws exception if connection can not be made
     */
    protected InputStream getStream(String extension, boolean firstconnect) throws Exception {
        if (ZangleConnections.connected == false) {
            if (firstconnect != true) {
                throw new Exception("You are not connected!", new NotConnectedException());
            }
        }

        URLConnection con = new URL(ZangleConnections.mainzangleurl + extension).openConnection();
        //String cookie = getCookies();

        //con.setRequestProperty("Cookie", cookie);
        cookieManager.setCookies(con);
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0");
        con.connect();


        cookieManager.storeCookies(con);
        return con.getInputStream();
    }

    /**
     * Gets HTTP data from zangle url
     *
     * @param extension    Extension of page, ex. stusel.aspx
     * @param firstconnect If connecting for first time, omits not connected exception if set at true
     * @return Returns ArrayList of the response of the post
     * @throws Exception Throws exception if connection can not be made
     */
    protected ArrayList<String> get(String extension, boolean firstconnect) throws Exception {
        if (ZangleConnections.connected == false) {
            if (firstconnect != true) {
                throw new Exception("You are not connected!", new NotConnectedException());
            }
        }

        URLConnection con = new URL(ZangleConnections.mainzangleurl + extension).openConnection();
        //String cookie = getCookies();

        //con.setRequestProperty("Cookie", cookie);
        cookieManager.setCookies(con);
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0");
        con.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

        cookieManager.storeCookies(con);
        return response(br);

    }

    protected void quickGet(String extension, boolean firstconnect) throws Exception {
        if (ZangleConnections.connected == false) {
            if (firstconnect != true) {
                throw new Exception("You are not connected!", new NotConnectedException());
            }
        }
        URLConnection con = new URL(ZangleConnections.mainzangleurl + extension).openConnection();
        //String cookie = getCookies();

        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0");
        //con.setRequestProperty("Cookie", cookie);
        cookieManager.setCookies(con);
        con.connect();
        cookieManager.storeCookies(con);
    }

    /**
     * Method to post to the zangle url
     *
     * @param extension    Extension of url, or a page, like stusel.aspx
     * @param data         List of data being posted
     * @param firstconnect If connecting for first time, omits not connected exception if set at true
     * @throws Exception Thrown if connection can not be made
     */
    protected void quickPost(String extension, HashMap<String, String> data, boolean firstconnect) throws Exception {
        if (ZangleConnections.connected == false) {
            if (firstconnect != true) {
                throw new Exception("You are not connected!", new NotConnectedException());
            }
        }


        URLConnection con = new URL(ZangleConnections.mainzangleurl + extension).openConnection();
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0");
        //String cookie = getCookies();
        //System.out.println(cookie);

        //con.setRequestProperty("Cookie", cookie);
        cookieManager.setCookies(con);
        con.connect();

        byte[] byteData = getRequestParamString(data).getBytes("UTF-8");

        OutputStream outputStream = con.getOutputStream();

        outputStream.write(byteData);
        outputStream.flush();
        cookieManager.storeCookies(con);
        outputStream.close();
    }

    protected void saveSessionCookie() throws Exception {
        cookies.clear();
        URLConnection con = new URL(ZangleConnections.mainzangleurl + ZangleConstants.DEFAULT_EXTENSION).openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0");

        con.connect();

        String headerName;
        for (int i = 1; (headerName = con.getHeaderFieldKey(i)) != null; i++) {
            if (headerName.equals("Set-Cookie")) {
                String cookie = con.getHeaderField(i);
                cookie = cookie.substring(0, cookie.indexOf(";"));
                String cookieName = cookie.substring(0, cookie.indexOf("="));
                String cookieValue = cookie.substring(cookie.indexOf("=") + 1, cookie.length());
                cookies.add((cookieName + "=" + cookieValue).trim());
            }
        }
    }

    private String getCookies() {
        String cookieString = "";
        String[] cookiesArray = cookies.toArray(new String[0]);
        int size = cookiesArray.length;
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                cookieString = cookieString + cookiesArray[i];
                cookieString.trim();
                break;
            }
            cookieString = cookieString + cookiesArray[i] + "; ";
        }
        return cookieString;
    }

    private String getRequestParamString(HashMap<String, String> data) {
        String request = "";
        Iterator<String> iterator = data.keySet().iterator();
        String param = "";
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = data.get(key);
            param = key + "=" + value;
            request = request + param + "&";
        }
        request = request.replace(param + "&", param);
        return request;
    }

}
