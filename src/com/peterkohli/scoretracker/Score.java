package com.peterkohli.scoretracker;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: pbkoh_000
 * Date: 6/9/15
 * Time: 4:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Score implements DatabaseObject {
    private Golfer player;
    private Course golfCourse;
    private Date datePlayed;
    private int[] holeByHoleScore;
    private String tees;

    @Override
    public void write(Connection conn) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Connection conn) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
