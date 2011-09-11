/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zangleapitest;

import com.darkscripting.zangle.ZangleConnection;

import javax.swing.*;

/**
 * @author User
 */
public class LoginUtil implements Runnable {

    private static LoginUtil instance;

    public static LoginUtil getInstance() {
        if (instance == null) {
            instance = new LoginUtil();
        }
        return instance;
    }

    private LoginUtil() {
    }

    public static ZangleConnection con = new ZangleConnection();
    private static String zangleurl = "https://webconnect.bloomfield.org/Zangle/StudentConnect/";
    private static String user;
    private static String pass;

    public boolean login(String username, String password) {
        try {
            con.connect(zangleurl, username, password);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void startLogin(String username, String password) {
        user = username;
        pass = password;
        Thread thread = new Thread(new LoginUtil());
        thread.start();
    }

    public void run() {
        if (login(user, pass) == true) {
            JOptionPane.showMessageDialog(null, "Logged in!", "Information", JOptionPane.INFORMATION_MESSAGE);
            LoginGUI.getInstance().dispose();
            MainGUI.getInstance().create();
            return;
        } else {
            LoginGUI.getInstance().newLogin();
            JOptionPane.showMessageDialog(null, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

    }
}
