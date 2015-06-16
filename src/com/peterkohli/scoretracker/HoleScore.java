package com.peterkohli.scoretracker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by pbkoh_000 on 6/16/2015.
 */
public class HoleScore implements DatabaseObject {
    private int strokes;
    private int toGreen;
    private boolean sand;
    private boolean penalty;
    private String drive;
    private int hole;
    private Score round;

    public HoleScore(Score round, int strokes, int toGreen, boolean sand, boolean penalty, String drive, int hole){
        this.round = round;
        this.strokes = strokes;
        this.toGreen = toGreen;
        this.sand = sand;
        this.penalty = penalty;
        this.drive = drive;
        this.hole = hole;
    }


    @Override
    public void write(Connection conn) throws SQLException {


        //To change body of implemented methods use File | Settings | File Templates.
        String Query = "INSERT INTO dbo.HoleScore (" +
                " RoundID," +
                " Strokes," +
                " ToGreen," +
                " Penalty," +
                " Sand," +
                " Drive," +
                " Hole)" +
                " Values (?, ?, ?, ?, ?, ?, ?)";

        try {
            //set preparedstatement parameters for writing single hole score
            PreparedStatement st = conn.prepareStatement(Query);
            st.setLong(1, round.getRoundID());
            st.setInt(2, strokes);
            st.setInt(3, toGreen);
            st.setBoolean(4, penalty);
            st.setBoolean(5, sand);
            st.setString(6, drive);
            st.setInt(7, hole);

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
