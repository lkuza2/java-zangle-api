package com.darkscripting.zangle.connections;

import com.darkscripting.zangle.assignment.ZAssignment;
import com.darkscripting.zangle.assignment.ZangleAssignment;
import com.darkscripting.zangle.classes.ZClass;
import com.darkscripting.zangle.classes.ZangleClass;
import com.darkscripting.zangle.constants.ZangleConstants;
import com.darkscripting.zangle.object.ZangleObject;
import com.darkscripting.zangle.student.ZStudent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.InputStream;
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
        InputStream studentPage = http.getStream(ZangleConstants.STUDENT_SEL_EXTENSION, true);
        //ArrayList<String> studentDem = http.get(ZangleConstants.STUDENT_DEM_EXTENSION, true);
        ZStudent student = ZangleConnections.zstudent;


        Document document = Jsoup.parse(studentPage, null, ZangleConnections.mainzangleurl + ZangleConstants.STUDENT_SEL_EXTENSION);

        Elements studentInfo = document.getElementsByAttributeValue("class", "odd sturow").get(0).getElementsByAttributeValue("style", "white-space: nowrap;");

        student.setName(studentInfo.get(0).text());
        student.setGrade(studentInfo.get(1).text());
        student.setSchool(studentInfo.get(2).text());
        String studentID = document.getElementsByAttributeValue("class", "odd sturow").get(0).id();
        studentPage.close();

        http.quickGet(ZangleConstants.STUDENT_SELECTION_EXTENSION + studentID, true);

        InputStream studentAssign = http.getStream(ZangleConstants.STUDENT_ASSIGN_EXTENSION, true);
        document = Jsoup.parse(studentAssign, null, ZangleConnections.mainzangleurl + ZangleConstants.STUDENT_ASSIGN_EXTENSION);
        Elements classes = document.getElementsByAttributeValue("class", "txtin3 displaytbl");

        for (int i = 0; i < classes.size(); i++) {
            Elements classInfo = classes.get(i).getElementsByAttributeValue("class", "tblhdr2 mediumHighlight").get(0).getElementsByTag("td");
            Elements gradeInfo = classes.get(i).getElementsByAttributeValue("class", "tblhdr2 mediumHighlight").get(1).getElementsByTag("td");

            String className = classInfo.get(0).getElementsByTag("b").get(1).text();
            String period = classInfo.get(0).getElementsByTag("td").get(0).html().split("<b>")[1].split("</b>")[1].trim();
            String grade = gradeInfo.get(0).text().split(":")[1].trim();
            if (!grade.contains("not"))
                grade = grade.split("\\(")[0].trim();
            String teacherUnparsed[] = classInfo.get(1).text().split(":")[1].trim().split(",");
            String teacher = teacherUnparsed[1].trim() + " " + teacherUnparsed[0];

            className = className.split("\\(")[0].trim();
            zclass.addClass(new ZClass(className, period, grade, teacher, ""));

            //Add assignments
            Elements assignments = classes.get(i).getElementsByTag("tbody").get(0).getElementsByTag("tr");

            for (int j = 0; j < assignments.size(); j++) {
                if (assignments.size() == 1 && assignments.get(0).text().contains("No"))
                    break;

                Elements assignmentInfo = assignments.get(j).getElementsByTag("td");

                String dueDate = assignmentInfo.get(1).text();
                String assignmentName = assignmentInfo.get(3).text();
                String points = assignmentInfo.get(4).text();
                String score = assignmentInfo.get(5).text();
                if (score.isEmpty())
                    score = "0";

                boolean extraCredit = assignmentInfo.get(assignmentInfo.size() - 2).html().contains("check");
                boolean notGraded = assignmentInfo.get(assignmentInfo.size() - 1).html().contains("check");

                ZAssignment assignment = zclass.getClass(i).getAssignments().addAssignment(new ZAssignment(dueDate, assignmentName, points, score, "", extraCredit, notGraded));
                setAssignmentPercent(assignment);

            }

            setClassPercents(zclass);
        }
        studentAssign.close();
    }

    /**
     * Returns the parsed detail of the assignment
     *
     * @param assignmentID Assignment to find
     * @return Returns parsed String with details, \n denoting a new line
     * @throws Exception Throws exception if there is a problem
     */
    public String returnAssignmentDetails(String assignmentID) throws Exception {
        String assignmentURL = ZangleConstants.ASSIGNMENT_DESCRIPTION_EXTENSION + assignmentID;
        ArrayList<String> page = http.get(assignmentURL, true);
        StringBuffer stringBuffer = new StringBuffer();

        String line;
        int arrayindex = 0;

        do {
            line = page.get(arrayindex);
            if (line.contains(ZangleConstants.ASSIGNMENT_DETAILS_PAGE)) {
                if (line.contains(ZangleConstants.ASSIGNMENT_DETAILS_TITLE)) {
                    stringBuffer.append(line.replace(ZangleConstants.ASSIGNMENT_DETAILS_TITLE, "").replace("</b>&nbsp;</td>", "") + "\n");
                } else {
                    stringBuffer.append(line.replace("<td valign=\"top\">", "").replace("</td>", "") + "\n");
                }
            }

            arrayindex++;
        }
        while (!line.contains(ZangleConstants.END_OF_DETAILS_PAGE));
        return stringBuffer.toString();
    }

    /**
     * Returns the assignment ID to retrieve description
     *
     * @param description The unparsed line that contains the ID
     * @return Parsed ID of the assignment
     */
    private String parseForAssignmentID(String description) {
        return description.split("'")[1];
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

    /**
     * Sets the percent score for an assignment
     *
     * @param assignment The assignment to set the percent for
     */
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
