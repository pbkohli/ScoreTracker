package com.peterkohli.scoretracker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by pbkoh_000 on 6/16/2015.
 */
public class HoleScore {
    private int strokes;
    private int toGreen;
    private boolean sand;
    private boolean penalty;
    private String drive;
    private int hole;


    public HoleScore(int strokes, int toGreen, boolean sand, boolean penalty, String drive, int hole){
        this.strokes = strokes;
        this.toGreen = toGreen;
        this.sand = sand;
        this.penalty = penalty;
        this.drive = drive;
        this.hole = hole;


    }


    //@Override
    public void write(Connection conn, long roundID) throws SQLException {


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
            st.setLong(1, roundID);
            st.setInt(2, strokes);
            st.setInt(3, toGreen);
            st.setBoolean(4, penalty);
            st.setBoolean(5, sand);
            st.setString(6, drive);
            st.setInt(7, hole);

            st.executeUpdate();
            st.close();

        } catch (SQLException e) {
            System.err.println("Failed to write hole score to database.");
            System.err.println(e.getMessage());
        }

    }

    //@Override
    public void delete(Connection conn, long roundID, int holeNumber) throws SQLException {
        String Query = "Delete FROM HoleScore WHERE RoundID = ? AND Hole = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(Query);
            ps.setLong(1, roundID);
            ps.setInt(2, holeNumber);

            ps.executeUpdate();

            ps.close();
        } catch (SQLException se) {
            throw se;
        }

    }

    public int getHoleNumber(){
        return this.hole;
    }

    public int getScore() { return this.strokes;}

    public String getDrive() { return this.drive;}

    public boolean getSand() { return this.sand; }

    public boolean getPenalty() { return this.penalty; }

    public int getToGreen() { return this.toGreen; }

}
