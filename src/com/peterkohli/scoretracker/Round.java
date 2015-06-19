package com.peterkohli.scoretracker;

import java.sql.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: pbkoh_000
 * Date: 6/9/15
 * Time: 4:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Round implements DatabaseObject {
    private Golfer player;
    private Course golfCourse;
    private Date datePlayed;
    private HoleScore[] holeByHoleScore;
    private long roundID;

    public Round(Golfer golfer, Course course, Date date, HoleScore[] holeScores){
        this.player = golfer;
        this.golfCourse = course;
        this.datePlayed = date;
        this.holeByHoleScore = holeScores;
    }

    @Override
    public void write(Connection conn)
            throws SQLException {

        String roundQuery = "INSERT INTO dbo.Round ("
                + " UserID,"
                + " CourseID,"
                + " [Date]) VALUES ("
                + "?, ?, ?)";

        try {
            //set preparedstatement parameters for writing score
            PreparedStatement st = conn.prepareStatement(roundQuery, Statement.RETURN_GENERATED_KEYS);
            st.setLong(1, player.getUserID(conn));
            st.setInt(2, golfCourse.getCourseID(conn));
            st.setDate(3, (java.sql.Date) datePlayed);


            //write out course entries
            st.executeUpdate();
            //get RoundID of entered round
            ResultSet generatedKey = st.getGeneratedKeys();
                if(generatedKey.next()) this.roundID = generatedKey.getLong(1);
            st.close();


        } catch (SQLException e) {
            System.err.println("Failed to write score to database.");
            System.err.println(e.getMessage());
        }


        for(int i = 0; i < holeByHoleScore.length; i++){
            holeByHoleScore[i].write(conn, roundID);

        }
    }

    @Override
    public void delete(Connection conn) throws SQLException {
        for(int i = 0; i < holeByHoleScore.length; i++){
            holeByHoleScore[i].delete(conn, roundID, i + 1);
        }

        String Query = "Delete FROM dbo.Round WHERE RoundID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(Query);
            ps.setLong(1, roundID);

            ps.executeUpdate();

            ps.close();
        } catch (SQLException se) {
            throw se;
        }
    }

    public long getRoundID() {
        return this.roundID;
    }

    public int getScore() {
        int score = 0;
        for (int i = 0; i < holeByHoleScore.length; i++){
        score = score + holeByHoleScore[i].getScore();
        }
        return score;
    }

    public int[] getDrives() {
        int drives[] = {0, 0, 0};
        for (int i = 0; i < holeByHoleScore.length; i++) {
            if (holeByHoleScore[i].getDrive() == "L") {
                drives[0] = drives[0] + 1;
            } else if (holeByHoleScore[i].getDrive() == "S") {
                drives[1] = drives[1] + 1;
            } else if (holeByHoleScore[i].getDrive() == "R") {
                drives[2] = drives[2] + 1;
            }
        }
        return drives;
    }

}
