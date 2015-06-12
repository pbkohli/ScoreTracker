package com.peterkohli.scoretracker;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: pbkoh_000
 * Date: 6/12/15
 * Time: 4:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class CourseTest {

    @Test
    public void testSetHoles() throws Exception {
        Course course1 = new Course("Sakonnet", "RI", "Golds");

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

        Assert.assertEquals(5, course1.getHolePar(3));
        Assert.assertEquals(13, course1.getHoleHandicap(18));
    }


}
