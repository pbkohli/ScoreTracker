package com.peterkohli.scoretracker;

import java.sql.Connection;
import java.sql.SQLException;
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

    @Override
    public void write(Connection conn) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Connection conn) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
