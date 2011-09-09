package com.darkscripting.zangle.connections;

import com.darkscripting.zangle.exceptions.NotConnectedException;
import com.darkscripting.zangle.response.ZangleResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
     * Constructor
     */
    private ZangleHttp() {

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
    protected ArrayList<String> post(String extension, List<NameValuePair> data, boolean firstconnect) throws Exception {
        if (ZangleConnections.connected == false) {
            if (firstconnect != true) {
                throw new Exception("You are not connected!", new NotConnectedException());
            }
        }
        HttpPost httpost = new HttpPost(ZangleConnections.mainzangleurl + extension);

        List<NameValuePair> nvps = data;

        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

        HttpResponse response = ZangleConnections.httpclient.execute(httpost);
        HttpEntity entity = response.getEntity();

        BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));

        return response(br, entity);
    }

    /**
     * Gets HTTP data from zangle url
     *
     * @param extension    Extensionof page, ex. stusel.aspx
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
        HttpGet httpget = new HttpGet(ZangleConnections.mainzangleurl + extension);

        HttpResponse response = ZangleConnections.httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();


        BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));

        return response(br, entity);

    }

    protected void quickGet(String extension, boolean firstconnect) throws Exception {
        if (ZangleConnections.connected == false) {
            if (firstconnect != true) {
                throw new Exception("You are not connected!", new NotConnectedException());
            }
        }
        HttpGet httpget = new HttpGet(ZangleConnections.mainzangleurl + extension);

        HttpResponse response = ZangleConnections.httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        entity.consumeContent();
    }

    /**
     * Method to post to the zangle url
     *
     * @param extension    Extension of url, or a page, like stusel.aspx
     * @param data         List of data being posted
     * @param firstconnect If connecting for first time, omits not connected exception if set at true
     * @throws Exception Thrown if connection can not be made
     */
    protected void quickPost(String extension, List<NameValuePair> data, boolean firstconnect) throws Exception {
        if (ZangleConnections.connected == false) {
            if (firstconnect != true) {
                throw new Exception("You are not connected!", new NotConnectedException());
            }
        }
        HttpPost httpost = new HttpPost(ZangleConnections.mainzangleurl + extension);

        List<NameValuePair> nvps = data;

        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

        HttpResponse response = ZangleConnections.httpclient.execute(httpost);
        HttpEntity entity = response.getEntity();
        entity.consumeContent();
    }

}
