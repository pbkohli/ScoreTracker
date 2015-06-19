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


        HoleScore holeScore1 = new HoleScore(6, 3, false, false, "L", 1);
        HoleScore holeScore2 = new HoleScore(4, 2, false, false, "", 2);
        HoleScore holeScore3 = new HoleScore(8, 6, false, true, "L", 3);
        HoleScore holeScore4 = new HoleScore(5, 2, false, false, "R", 4);
        HoleScore holeScore5 = new HoleScore(5, 3, true, false, "R", 5);
        HoleScore holeScore6 = new HoleScore(4, 1, false, false, "", 6);
        HoleScore holeScore7 = new HoleScore(6, 3, false, false, "S", 7);
        HoleScore holeScore8 = new HoleScore(6, 3, false, false, "R", 8);
        HoleScore holeScore9 = new HoleScore(6, 3, false, false, "", 9);
        HoleScore holeScore10 = new HoleScore(4, 3, false, false, "R", 10);
        HoleScore holeScore11 = new HoleScore(6, 4, false, true, "R", 11);
        HoleScore holeScore12 = new HoleScore(7, 5, true, false, "R", 12);
        HoleScore holeScore13 = new HoleScore(6, 3, false, false, "L", 13);
        HoleScore holeScore14 = new HoleScore(6, 3, false, false, "R", 14);
        HoleScore holeScore15 = new HoleScore(4, 2, false, false, "S", 15);
        HoleScore holeScore16 = new HoleScore(4, 2, true, false, "", 16);
        HoleScore holeScore17 = new HoleScore(6, 4, false, false, "R", 17);
        HoleScore holeScore18 = new HoleScore(4, 3, false, false, "R", 18);

        HoleScore[] holeByHoleScore = {holeScore1
        , holeScore2
        , holeScore3
        , holeScore4
        , holeScore5
        , holeScore6
        , holeScore7
        , holeScore8
        , holeScore9
        , holeScore10
        , holeScore11
        , holeScore12
        , holeScore13
        , holeScore14
        , holeScore15
        , holeScore16
        , holeScore17
        , holeScore18};

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

        System.out.println(round1.getScore());

        StringBuilder sb = new StringBuilder("You hit ");
        sb.append(round1.getDrives()[1]);
        sb.append(" out of ");
        sb.append(round1.getDrives()[0] + round1.getDrives()[1] + round1.getDrives()[2]);
        sb.append(" drives in the fairway.");

        System.out.println(sb);

        StringBuilder sb1 = new StringBuilder("You missed ");
        sb1.append(round1.getDrives()[0]);
        sb1.append(" out of ");
        sb1.append(round1.getDrives()[0] + round1.getDrives()[1] + round1.getDrives()[2]);
        sb1.append(" drives to the right.");

        System.out.println(sb1);

        StringBuilder sb2 = new StringBuilder("You missed ");
        sb2.append(round1.getDrives()[2]);
        sb2.append(" out of ");
        sb2.append(round1.getDrives()[0] + round1.getDrives()[1] + round1.getDrives()[2]);
        sb2.append(" drives to the left.");

        System.out.println(sb2);



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
