package com.univ.dao;

import java.sql.*;
import java.util.*;
import com.univ.bean.Course;
import com.univ.util.DBUtil;

public class CourseDAO {

    public Course findCourse(String courseID) {
        Course c = null;
        try {
            Connection con = DBUtil.getDBConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM COURSE_TBL WHERE COURSE_ID=?");
            ps.setString(1, courseID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                c = new Course();
                c.setCourseID(rs.getString(1));
                c.setCourseTitle(rs.getString(2));
                c.setCredits(rs.getInt(3));
                c.setCapacity(rs.getInt(4));
                c.setEnrolledCount(rs.getInt(5));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return c;
    }

    public List<Course> viewAllCourses() {
        List<Course> list = new ArrayList<>();
        try {
            Connection con = DBUtil.getDBConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM COURSE_TBL");
            while (rs.next()) {
                Course c = new Course();
                c.setCourseID(rs.getString(1));
                c.setCourseTitle(rs.getString(2));
                c.setCredits(rs.getInt(3));
                c.setCapacity(rs.getInt(4));
                c.setEnrolledCount(rs.getInt(5));
                list.add(c);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public boolean insertCourse(Course c) {
        try {
            Connection con = DBUtil.getDBConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO COURSE_TBL VALUES (?,?,?,?,?)");
            ps.setString(1, c.getCourseID());
            ps.setString(2, c.getCourseTitle());
            ps.setInt(3, c.getCredits());
            ps.setInt(4, c.getCapacity());
            ps.setInt(5, c.getEnrolledCount());
            ps.executeUpdate();
            con.commit();
            return true;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean updateEnrolledCount(String courseID, int count) throws Exception {
        Connection con = DBUtil.getDBConnection();
        PreparedStatement ps = con.prepareStatement(
            "UPDATE COURSE_TBL SET ENROLLED_COUNT=? WHERE COURSE_ID=?");
        ps.setInt(1, count);
        ps.setString(2, courseID);
        return ps.executeUpdate() > 0;
    }

    public boolean deleteCourse(String courseID) throws Exception {
        Connection con = DBUtil.getDBConnection();
        PreparedStatement ps = con.prepareStatement(
            "DELETE FROM COURSE_TBL WHERE COURSE_ID=?");
        ps.setString(1, courseID);
        return ps.executeUpdate() > 0;
    }
}
