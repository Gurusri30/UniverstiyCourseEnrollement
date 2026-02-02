package com.univ.service;

import java.sql.Connection;
import java.sql.Date;
import com.univ.bean.*;
import com.univ.dao.*;
import com.univ.util.*;

public class EnrollmentService {

    private CourseDAO courseDAO = new CourseDAO();
    private EnrollmentDAO enrollDAO = new EnrollmentDAO();

    public boolean enrollStudent(String courseID, String studentID, String studentName)
            throws Exception {

        if (courseID.isEmpty() || studentID.isEmpty() || studentName.isEmpty())
            throw new ValidationException();

        Course c = courseDAO.findCourse(courseID);
        if (c == null) return false;

        if (c.getEnrolledCount() >= c.getCapacity())
            throw new CourseFullException();

        Connection con = DBUtil.getDBConnection();
        try {
            int newCount = c.getEnrolledCount() + 1;
            courseDAO.updateEnrolledCount(courseID, newCount);

            Enrollment e = new Enrollment();
            e.setEnrollmentID(enrollDAO.generateEnrollmentID());
            e.setCourseID(courseID);
            e.setStudentID(studentID);
            e.setStudentName(studentName);
            e.setEnrollmentDate(new Date(System.currentTimeMillis()));
            e.setStatus("ENROLLED");

            enrollDAO.recordEnrollment(e);
            con.commit();
            return true;
        } catch (Exception ex) {
            con.rollback();
            return false;
        }
    }

    public boolean dropEnrollment(int enrollmentID) throws Exception {
        if (enrollmentID <= 0) throw new ValidationException();

        Connection con = DBUtil.getDBConnection();
        try {
            enrollDAO.closeEnrollment(enrollmentID);
            con.commit();
            return true;
        } catch (Exception e) {
            con.rollback();
            return false;
        }
    }
}
