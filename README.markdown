#Java-Zangle-API

This is the project for the Java Zangle API written in Java.

## Changelog

* Version 1.05 BETA
    * Added getPercent() function for classes to get students current     percent in the class.
    * Added isExtraCredit and isNotGraded methods for ZAssignment class.
    * Added getPercent and setPercent for assignments.
    * Updated update() method in ZangleConnections.  Now refreshes session data automatically if expired.
    * Added new method in ZangleConnections. isLoggedIn(), checks if the user is actually logged in with VALID session data
    * Code optimizations and cleanup