package com.peterkohli.scoretracker;

import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * Creator: Peter Kohli
 * Date: 6/12/15
 * Time: 10:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class ScoreTracker {
    public static void main(String[] args) {

        //create two courses using the constructor
        Course course1 = new Course("Sakonnet", "RI", "Blues");
        Course course2 = new Course("Brae Burn", "MA", "Golds");

        //set up multidimensional array of holes
        int[][] holes1 = {
                {4, 6, 377},
                {3, 18, 180},
                {5, 12, 477},
                {4, 2, 404},
                {4, 16, 364},
                {3, 14, 213},
                {4, 4, 360},
                {4, 8, 342},
                {3, 10, 176},
                {4, 1, 379},
                {4, 11, 359},
                {5, 7, 465},
                {4, 5, 413},
                {4, 3, 376},
                {4, 9, 407},
                {3, 17, 159},
                {4, 10, 320},
                {4, 13, 340}
        };

        //assign scores to course
        course1.setHoles(holes1);
        course1.setSlope(122);
        course1.setRating(69.7);


        //Establish a connection with the SQL Server and DB. conn is now a connection to the db    \
        //http://jtds.sourceforge.net/faq.html#urlFormat
        //http://alvinalexander.com/java/edu/pj/pj010024
        Connection conn = null;
        try {
            //Step 1: "Load" the JDBC driver
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            //Step 2: Establish the connection to the database
            String url = "jdbc:jtds:sqlserver://localhost:1433/ScoreTracker";
            conn = DriverManager.getConnection(url, "sa", "gotaxgopher");
        } catch (Exception e) {
            System.err.println("Failed to connect to DB.");
            System.err.println(e.getMessage());
        }

        try {
            course1.write(conn);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        try {
            course1.getHoles(conn);
        } catch (SQLException e){
            e.printStackTrace();
        }

        System.out.println(course1.getHolePar(2));


        int courseID = 0;
        try {
            courseID = course1.getCourseID(conn);
        } catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println(courseID);


        Date dob = java.sql.Date.valueOf("1990-12-17");
        Golfer golfer1 = new Golfer("pbkohli@gmail.com", "Peter", "Kohli", dob);

        Date datePlayed = java.sql.Date.valueOf("2015-06-14");


        HoleScore holeScore1 = new HoleScore(4, 2, false, false, "S", 1);
        HoleScore[] holeByHoleScore = {holeScore1};
        Round round1 = new Round(golfer1, course1, datePlayed, holeByHoleScore);

        try {
            golfer1.write(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            round1.write(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            round1.delete(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            course1.delete(conn);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        try {
            golfer1.delete(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
