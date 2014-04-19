package com.lukekuza.mistar.object;

import com.lukekuza.mistar.classes.ZangleClass;
import com.lukekuza.mistar.student.ZStudent;

/**
 * Represnts all ZangleObjects.  All objects that need these variables or contain student data extend off
 * of this class
 *
 * @author Luke Kuza
 * @version 1.00
 */
public class ZangleObject {

    /**
     * ZStudent creation
     */
    protected static ZStudent zstudent = new ZStudent();
    /**
     * ZangleClass creation
     */
    protected static ZangleClass zclass = new ZangleClass();

}
