package org.geotools.tutorial.quickstart;



import de.micromata.opengis.kml.v_2_2_0.Coordinate;

import java.util.List;

public class KmlLine {
    private List<Coordinate> points;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Coordinate> getPoints() {
        return points;
    }

    public void setPoints(List<Coordinate> points) {
        this.points = points;
    }
}