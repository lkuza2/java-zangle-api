/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zangleapitest;

import javax.swing.*;

/**
 * @author User
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JOptionPane.showMessageDialog(null, "<html>This program was created by Luke Kuza to test the ZangleAPI<br>"
                    + " The ZangleAPI was created by Luke Kuza<br>"
                    + "The current version you are testing is 1.08 BETA</html>", "Information", JOptionPane.INFORMATION_MESSAGE);

            LoginGUI.getInstance().create();
        } catch (Exception ex) {
            //Do nothing
        }
    }
}
