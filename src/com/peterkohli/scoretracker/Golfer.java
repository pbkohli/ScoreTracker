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
            System.err.println("Failed to write course to database.");
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(Connection conn) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
