/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zangleapitest;

import com.darkscripting.zangle.assignment.ZAssignment;
import com.darkscripting.zangle.assignment.ZangleAssignment;
import com.darkscripting.zangle.classes.ZClass;
import com.darkscripting.zangle.classes.ZangleClass;

/**
 * @author User
 */
public class MainUtil {

    private static MainUtil instance;

    public static MainUtil getInstance() {
        if (instance == null) {
            instance = new MainUtil();
        }
        return instance;
    }

    private MainUtil() {
    }

    public void addClasses() {
        try {
            MainGUI mg = MainGUI.getInstance();
            ZangleClass classes = LoginUtil.con.student().getClasses();
            int size = classes.getSize();
            int index = 0;
            while (size > 0) {
                ZClass zclass = classes.getClass(index);
                mg.addClass(zclass.getName());
                size--;
                index++;
            }
        } catch (Exception ex) {
        }
    }

    public void openClass(int index) {
        try {
            ClassGUI.getInstance().create(index);
            ZClass zclass = LoginUtil.con.student().getClasses().getClass(index);
            ClassGUI cg = ClassGUI.getInstance();
            cg.setDialogTitle(zclass.getName());
            cg.set(zclass.getName(), zclass.getPeriod(), zclass.getTeacher(), zclass.getGrade(), zclass.getPercent(),
                    zclass.getEmail(), index);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void addAssignments(int index) {
        try {
            ClassGUI cg = ClassGUI.getInstance();
            ZangleAssignment assignment = LoginUtil.con.student().getClasses().getClass(index).getAssignments();
            int size = assignment.getSize();
            int assignmentindex = 0;
            while (size > 0) {
                ZAssignment zassignment = assignment.getAssignment(assignmentindex);
                cg.addAssignment(zassignment.getAssignmentName());
                size--;
                assignmentindex++;
            }
        } catch (Exception ex) {
        }
    }

    public void openAssignment(int index, int classindex) {
        try {
            AssignmentGUI.getInstance().create();
            ZAssignment zassignment = LoginUtil.con.student().getClasses().getClass(classindex).getAssignments().getAssignment(index);
            AssignmentGUI ag = AssignmentGUI.getInstance();
            ag.setDialogTitle(zassignment.getAssignmentName());
            ag.set(zassignment.getAssignmentName(), zassignment.getPoints(), zassignment.getScore(), zassignment.getDueDate(), zassignment.getPercent(), zassignment.isExtraCredit(), zassignment.isNotGraded());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
