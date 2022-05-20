package org.geotools.tutorial.quickstart;

import junit.framework.TestCase;
import org.junit.Test;
import org.locationtech.jts.geom.Coordinate;

public class MyUtilTest extends TestCase {

    @Test
    public void test(){
        MyUtil myUtil = new MyUtil();

        Coordinate a = new Coordinate(30.27753822563512,104.4385657305434);
        Coordinate b = new Coordinate(30.33472327739947,104.4660303729687);

        double distance = myUtil.calculateDistance2D(a, b);
        System.out.println(distance);


    }

}