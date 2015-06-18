package com.peterkohli.scoretracker;

import net.sourceforge.jtds.jdbc.DateTime;

import java.sql.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: pbkoh_000
 * Date: 6/9/15
 * Time: 4:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class Golfer implements DatabaseObject {
    private String firstName;
    private String lastName;
    private Date dob;
    private String email;
    private long userID;

    public Golfer(String email, String firstName, String lastName, Date dob){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;

    }

    @Override
    public void write(Connection conn) throws SQLException {

        String Query = "INSERT INTO [dbo].[User] (" +
                " FirstName," +
                " LastName," +
                " Email," +
                " dob)" +
                " Values (?, ?, ?, ?)";

        try {
            //set preparedstatement parameters for writing user
            PreparedStatement st = conn.prepareStatement(Query);
            st.setString(1, firstName);
            st.setString(2, lastName);
            st.setString(3, email);
            st.setDate(4, (java.sql.Date) dob);

            st.executeUpdate();
            st.close();

        } catch (SQLException e) {
            System.err.println("Failed to write golfer to database.");
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(Connection conn) throws SQLException {
        String Query = "Delete FROM dbo.[User] WHERE Email = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(Query);
            ps.setString(1, email);

            ps.executeUpdate();

            ps.close();
        } catch (SQLException se) {
            throw se;
        }
    }

    //return sql userID for a given user
    public long getUserID(Connection conn)
            throws SQLException {
        int userID = 0;

        String selectQuery = "SELECT UserID FROM dbo.[User] WHERE Email = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(selectQuery);
            ps.setString(1, this.email);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userID = rs.getInt("UserID");
            }

            ps.close();
            rs.close();

        } catch (SQLException se) {
            throw se;
        }
        return userID;
    }
}
