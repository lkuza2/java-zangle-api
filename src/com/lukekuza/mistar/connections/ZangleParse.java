package com.lukekuza.mistar.connections;

import com.lukekuza.mistar.assignment.ZAssignment;
import com.lukekuza.mistar.assignment.ZangleAssignment;
import com.lukekuza.mistar.classes.ZangleClass;
import com.lukekuza.mistar.classes.ZClass;
import com.lukekuza.mistar.constants.ZangleConstants;
import com.lukekuza.mistar.object.ZangleObject;
import com.lukekuza.mistar.student.ZStudent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

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
}
