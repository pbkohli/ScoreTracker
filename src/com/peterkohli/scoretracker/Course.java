package com.peterkohli.scoretracker;

import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: pbkoh_000
 * Date: 6/9/15
 * Time: 4:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class Course implements DatabaseObject {
    private int[][] holes;
    private double slope;
    private double rating;
    private String tees;
    private String name;
    private String state;

    //Course constructor (no "void" and method name = class name)
    public Course(String name, String state, String tees) {
        this.name = name;
        this.state = state;
        this.tees = tees;
    }

    public void setHoles(int[][] holes) {
        this.holes = holes;
    }

    public void setSlope(double slope) {
        this.slope = slope;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getHolePar(int hole) {
        return holes[hole - 1][0];
    }

    public int getHoleHandicap(int hole) {
        return holes[hole - 1][1];
    }

    public double getSlope() {
        return slope;
    }

    //method to write a course to sql db
    @Override
    public void write(Connection conn)
            throws SQLException {

        String courseQuery = "INSERT INTO dbo.Course ("
                + " Name,"
                + " [State],"
                + " Tees) VALUES ("
                + "?, ?, ?)";
        String holeQuery = "INSERT INTO dbo.Hole ("
                + " Number,"
                + " Par,"
                + " Handicap,"
                + " Yardage,"
                + " CourseID) VALUES ("
                + "?, ?, ?, ?, (SELECT CourseID FROM dbo.Course WHERE Name = ?))";
        try {
            //set preparedstatement parameters for writing course
            PreparedStatement st = conn.prepareStatement(courseQuery);
            st.setString(1, name);
            st.setString(2, state);
            st.setString(3, tees);

            //write out course entries
            st.executeUpdate();
            st.close();

            //write hole info to db
            for (int i = 0; i < 18; i++) {
                PreparedStatement hst = conn.prepareStatement(holeQuery);
                hst.setString(1, Integer.toString(i + 1));
                hst.setString(2, Integer.toString(holes[i][0]));
                hst.setString(3, Integer.toString(holes[i][1]));
                hst.setString(4, Integer.toString(holes[i][2]));
                hst.setString(5, name);

                hst.executeUpdate();
                hst.close();
            }

        } catch (SQLException e) {
            System.err.println("Failed to write course to database.");
            System.err.println(e.getMessage());
        }
    }

    //PreparedStatement that adds the appropriate holes to a course
    public void getHoles(Connection conn)
            throws SQLException {
        int[][] holes = new int[18][3];

        //set query
        String query = "SELECT Number, Par, Handicap, Yardage FROM dbo.Hole " +
                "INNER JOIN dbo.Course ON dbo.Hole.CourseID = dbo.Course.CourseID " +
                "WHERE Name = ? AND [State] = ? AND Tees = ? ORDER BY Number";
        try {
            //prepare statement
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, this.name);
            ps.setString(2, this.state);
            ps.setString(3, this.tees);

            ResultSet rs = ps.executeQuery();

            //create array of holes
            int count = 0;
            while (rs.next()) {
                holes[count][0] = rs.getInt("Par");
                holes[count][1] = rs.getInt("Handicap");
                holes[count][2] = rs.getInt("Yardage");
                count++;
            }
            rs.close();
            ps.close();
            this.holes = holes;

        } catch (SQLException se) {
            throw se;
        }
    }

    //Delete course and its corresponding holes from DB
    @Override
    public void delete(Connection conn)
            throws SQLException {
        int courseID = 0;

        String selectQuery = "SELECT CourseID FROM dbo.Course WHERE Name = ? AND State = ? AND Tees = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(selectQuery);
            ps.setString(1, this.name);
            ps.setString(2, this.state);
            ps.setString(3, this.tees);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                courseID = rs.getInt("CourseID");
            }

            ps.close();
            rs.close();

        } catch (SQLException se) {
            throw se;
        }

        String deleteHolesQuery = "DELETE FROM dbo.Hole WHERE CourseID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(deleteHolesQuery);
            ps.setInt(1, courseID);

            ps.executeUpdate();

            ps.close();
        } catch (SQLException se) {
            throw se;
        }

        String deleteCourseQuery = "DELETE FROM dbo.Course WHERE CourseID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(deleteCourseQuery);
            ps.setInt(1, courseID);

            ps.executeUpdate();

            ps.close();
        } catch (SQLException se) {
            throw se;
        }
    }

    public int getCourseID(Connection conn)
            throws SQLException {
        int courseID = 0;

        String selectQuery = "SELECT CourseID FROM dbo.Course WHERE Name = ? AND State = ? AND Tees = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(selectQuery);
            ps.setString(1, this.name);
            ps.setString(2, this.state);
            ps.setString(3, this.tees);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                courseID = rs.getInt("CourseID");
            }

            ps.close();
            rs.close();

        } catch (SQLException se) {
            throw se;
        }
        return courseID;
    }



}
