package com.univ.dao;

import java.sql.*;
import com.univ.bean.Enrollment;
import com.univ.util.DBUtil;

public class EnrollmentDAO {

    public int generateEnrollmentID() throws Exception {
        Connection con = DBUtil.getDBConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT ENROLL_SEQ.NEXTVAL FROM DUAL");
        rs.next();
        return rs.getInt(1);
    }

    public boolean recordEnrollment(Enrollment e) throws Exception {
        Connection con = DBUtil.getDBConnection();
        PreparedStatement ps = con.prepareStatement(
            "INSERT INTO ENROLLMENT_TBL VALUES (?,?,?,?,?,?)");
        ps.setInt(1, e.getEnrollmentID());
        ps.setString(2, e.getCourseID());
        ps.setString(3, e.getStudentID());
        ps.setString(4, e.getStudentName());
        ps.setDate(5, e.getEnrollmentDate());
        ps.setString(6, e.getStatus());
        return ps.executeUpdate() > 0;
    }

    public boolean closeEnrollment(int id) throws Exception {
        Connection con = DBUtil.getDBConnection();
        PreparedStatement ps = con.prepareStatement(
            "UPDATE ENROLLMENT_TBL SET STATUS='DROPPED' WHERE ENROLLMENT_ID=?");
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    }
}
