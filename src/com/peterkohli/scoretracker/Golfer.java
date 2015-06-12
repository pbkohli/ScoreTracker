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

    public Golfer(String email, String firstName, String lastName, DateTime dob){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;

    }

    @Override
    public void write(Connection conn) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Connection conn) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
