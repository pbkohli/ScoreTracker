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
public class Score implements DatabaseObject {
    private Golfer player;
    private Course golfCourse;
    private Date datePlayed;
    private HoleScore[] holeByHoleScore;
    private long roundID;

    public Score(Golfer golfer, Course course, Date date){
        this.player = golfer;
        this.golfCourse = course;
        this.datePlayed = date;
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
            st.setInt(1, 1);
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
    }

    @Override
    public void delete(Connection conn) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public long getRoundID() {
        return this.roundID;
    }
}
