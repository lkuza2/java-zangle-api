package com.darkscripting.zangle.connections;

import com.darkscripting.zangle.assignment.ZAssignment;
import com.darkscripting.zangle.assignment.ZangleAssignment;
import com.darkscripting.zangle.classes.ZClass;
import com.darkscripting.zangle.classes.ZangleClass;
import com.darkscripting.zangle.constants.ZangleConstants;
import com.darkscripting.zangle.object.ZangleObject;
import com.darkscripting.zangle.student.ZStudent;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Class to parse and save/cache all information in one go to save time on later calls were reparsing
 * everything again is unnecessary.
 *
 * @author Luke Kuza
 * @version 1.01
 */
public class ZangleParse extends ZangleObject {


    /**
     * Instance of ZangleHttp for methods
     */
    private ZangleHttp http = ZangleHttp.getInstance();

    /**
     * Main parse method.  Parses and saves/caches all information on the student
     *
     * @throws Exception Throws exception if there is a problem
     */
    protected void parse() throws Exception {
        ArrayList<String> studentPage = http.get(ZangleConstants.STUDENT_SEL_EXTENSION, true);
        ArrayList<String> studentDem = http.get(ZangleConstants.STUDENT_DEM_EXTENSION, true);
        ArrayList<String> studentAssign = http.get(ZangleConstants.STUDENT_ASSIGN_EXTENSION, true);
        int index = 0;
        ZStudent student = ZangleConnections.zstudent;
        //When state == 2 done parsing
        int state = 0;

        do {
            String line = studentPage.get(index);

            if (line.contains(ZangleConstants.USERNAME_HTML_PARSE)) {
                student.setName(parseForName(studentPage.get(index + 1)));
                state++;
            }

            if (line.contains(ZangleConstants.GRADE_HTML_PARSE)) {
                //Skip 2 lines
                student.setGrade(parseForStudentGrade(studentPage.get(index + 1)));
                state++;
            }


            index++;
        } while (state != 2);

        index = 0;
        boolean set = false;
        do {

            String line = studentDem.get(index);

            if (line.contains(ZangleConstants.SCHOOL_HTML_PARSE)) {
                //Skip 3 lines
                student.setSchool(parseForSchool(studentDem.get(index + 3)));
                set = true;
            }
            index++;
        } while (set == false);

        int classIndex = 0;
        index = 0;
        int assignindex = 0;
        String line;
        //String line2;
        do {
            line = studentAssign.get(index);
            if (line.contains(ZangleConstants.CLASSES_HTML_PARSE)) {
                String period = parseForPeriod(studentAssign.get(index));
                //Skip 3 lines
                String className = parseForClass(studentAssign.get(index + 3));
                //Skip 6 lines
                String reverseteacher = parseForTeacher(studentAssign.get(index + 6));
                String email = null;

                if(reverseteacher.contains("@")){
                     email = reverseteacher.split("'")[1].replace("mailto:", "");
                    reverseteacher = reverseteacher.split(">")[1].replace("</a", "");

                }

                String[] teacherArray = reverseteacher.split(",");
                String teacher = teacherArray[1] + " " + teacherArray[0];


                //Skip 12 lines
                String grade = parseForGrade(studentAssign.get(index + 12));

                zclass.addClass(new ZClass(className, period, grade, teacher, email));
                assignindex++;
                do {
                    line = studentAssign.get(assignindex);
                    if (line.contains(ZangleConstants.ASSIGNMENT_HTML_PARSE)) {
                        String duedate = parseAssignementData(studentAssign.get(assignindex + 3));
                        String assignmentname = parseAssignementData(studentAssign.get(assignindex + 6));
                        String points = parseAssignementData(studentAssign.get(assignindex + 9));
                        String score = parseAssignementData(studentAssign.get(assignindex + 13));
                        String extracreditdata = studentAssign.get(assignindex + 19);
                        String notgradeddata = studentAssign.get(assignindex + 23);

                        boolean extracredit;
                        boolean notgraded;

                        if (extracreditdata.contains(ZangleConstants.ASSIGNMENT_EXTRA_CREDIT_NOT_GRADED_PARSE)) {
                            extracredit = true;
                        } else {

                            extracredit = false;
                        }


                        if (notgradeddata.contains(ZangleConstants.ASSIGNMENT_EXTRA_CREDIT_NOT_GRADED_PARSE)) {
                            notgraded = true;
                        } else {
                            notgraded = false;
                        }


                        ZAssignment assignment = zclass.getClass(classIndex).getAssignments().addAssignment(new ZAssignment(duedate, assignmentname, points, score, extracredit, notgraded));
                        setAssignmentPercent(assignment);
                    }
                    assignindex++;
                }
                while (!line.contains(ZangleConstants.CLASSES_HTML_PARSE) && !line.contains(ZangleConstants.END_OF_CLASSES_HTML_PARSE));

                classIndex++;
                index = assignindex - 2;
            }

            index++;
            assignindex++;
        } while (!line.contains(ZangleConstants.END_OF_CLASSES_HTML_PARSE));

        setClassPercents(zclass);
    }

    /**
     * Do maths to set the classes percents.  Do this here so it updates if new assignments come in.
     */
    private void setClassPercents(ZangleClass classes) throws Exception {
        int size = classes.getSize();
        ZClass zclass[] = classes.getClasses();

        for (int i = 0; i < size; i++) {
            ZangleAssignment assign = zclass[i].getAssignments();
            double total = 0.00;
            double scored = 0.00;
            int assignsize = assign.getSize();

            if (assignsize == -1) {
                zclass[i].setPercent(00.00);
                continue;
            }

            for (int j = 0; j < assignsize; j++) {
                ZAssignment assignment = assign.getAssignment(j);
                if (assignment.isNotGraded()) {
                    continue;
                }
                if (assignment.isExtraCredit()) {
                    scored = scored + Double.parseDouble(assignment.getScore());
                } else {
                    scored = scored + Double.parseDouble(assignment.getScore());
                    total = total + Double.parseDouble(assignment.getPoints());
                }
            }

            double decimal = scored / total;

            BigDecimal dec = BigDecimal.valueOf(decimal);

            BigDecimal fullpercent = dec.movePointRight(2);

            MathContext context = new MathContext(4, RoundingMode.UP);

            BigDecimal rounded = fullpercent.round(context);

            zclass[i].setPercent(rounded.doubleValue());

        }


    }

    private void setAssignmentPercent(ZAssignment assignment) {
        if (assignment.isExtraCredit()) {
            assignment.setPercent(00.00);
            return;
        } else {

            double score = Double.parseDouble(assignment.getScore());
            double points = Double.parseDouble(assignment.getPoints());


            BigDecimal decimal = BigDecimal.valueOf(score / points);

            BigDecimal percent = decimal.movePointRight(2);

            MathContext context = new MathContext(4, RoundingMode.UP);

            BigDecimal rounded = percent.round(context);

            assignment.setPercent(rounded.doubleValue());

        }


    }


    /**
     * Parses string for the class
     *
     * @param zclass Unparsed Class
     * @return Returns parsed string
     */
    private String parseForClass(String zclass) {
        return zclass.replace("&nbsp;", "").trim();
    }

    /**
     * Parses string for the teacher name
     *
     * @param unparsedteacher Unparsed string
     * @return Returns parsed string
     */
    private String parseForTeacher(String unparsedteacher) {
        return unparsedteacher.replace("&nbsp;&nbsp;", "").replace("Teacher:", "").replace(" ", "").trim();
    }

    /**
     * Parses unparsed String for grade
     *
     * @param unparsedgrade Unparsed string
     * @return Returns parsed string.  Returns null if no there is absolutely no grade
     */
    private String parseForGrade(String unparsedgrade) {
        String[] grade = unparsedgrade.replace("&nbsp;", "").split(":");
        if (grade.length < 2) {
            return null;
        } else
            return grade[1].trim();
    }

    private String parseForPeriod(String unparsedperiod) {
        return unparsedperiod.replace("&nbsp;&nbsp;", "").replace("Period:", "").trim();
    }

    /**
     * Parses for grade number
     *
     * @param unparsedgrade unparsed name
     * @return returns string
     */
    private String parseForStudentGrade(String unparsedgrade) {
        String[] grade = unparsedgrade.trim().split(" ");
        return grade[4];
    }

    /**
     * Parses for full name
     *
     * @param unparsedschool unparsed name
     * @return returns string
     */
    private String parseForSchool(String unparsedschool) {
        return unparsedschool.replace("&nbsp;", "").trim();
    }

    /**
     * Parses for full name
     *
     * @param unparsedusername unparsed name
     * @return returns string
     */
    private String parseForName(String unparsedusername) {
        return unparsedusername.replace("<b>", "").replace("</b>", "").replace("&nbsp;", " ").trim();
    }

    /**
     * Parses for data only from ASSIGNMENT VALUES including name, score, points possible and date <br>
     * All the assignments value above parse the same.  Therefore this the method to parse all of them
     *
     * @param unparsedassignmentdata unparsed assignment data
     * @return returns string
     */
    private String parseAssignementData(String unparsedassignmentdata) {
        return unparsedassignmentdata.replace("&nbsp;", "").trim();
    }
}
