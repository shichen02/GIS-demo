package org.geotools.tutorial.quickstart;

import de.micromata.opengis.kml.v_2_2_0.AltitudeMode;
import de.micromata.opengis.kml.v_2_2_0.Boundary;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.LineString;
import de.micromata.opengis.kml.v_2_2_0.LineStyle;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Style;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import java.io.File;
import java.io.FileNotFoundException;

class ParsingKmlUtilTest {

    public static void main(String[] args) {
        test02();
    }

    public static void test() {

        KmlProperty kmlProperty;
        ParsingKmlUtil parsingKmlUtil = new ParsingKmlUtil();
        File file = new File("D:\\test-file\\kml\\generate-Kml.kml");
        kmlProperty = parsingKmlUtil.parseKmlForJAK(file);

        if (kmlProperty.getKmlPoints().size() > 0) {
            for (KmlPoint k : kmlProperty.getKmlPoints()) {
                System.out.println("name : ");
                System.out.println(k.getName());
            }
            System.out.println("点");
        }
        if (kmlProperty.getKmlLines().size() > 0) {
            for (KmlLine k : kmlProperty.getKmlLines()) {
                System.out.println(k.getName());
            }
            System.out.println("线");
        }
        if (kmlProperty.getKmlPoints().size() > 0) {
            for (KmlPoint k : kmlProperty.getKmlPoints()) {
                System.out.println(k.getPoints());
            }
            System.out.println("面");


        }
    }

    static void test02() {
        final Kml kml = new Kml();

//        kml.createAndSetPlacemark()
//                .withName("London, UK").withOpen(Boolean.TRUE)
//                .createAndSetPoint()
//                .addToCoordinates(-0.126236, 51.500152);

        Document document = kml.createAndSetDocument();

        Style style = document.withName("path")
                .withOpen(true)
                .withDescription("test")
                .withId("yellowLineGreenPoly")
                .createAndAddStyle();


        style.withId("transBluePoly")
                .createAndSetPolyStyle()
                .withColor("7dff0000");

        style.createAndSetLineStyle()
                .withColor("7dff0000")
                .withWidth(10);


        LineString lineString = document.createAndAddPlacemark()
                .withStyleUrl("#transBluePoly")
                .createAndSetLineString();

        lineString.setAltitudeMode(AltitudeMode.ABSOLUTE);
        lineString.withExtrude(true);
        lineString.withTessellate(true);
        lineString.addToCoordinates(-122.0857412771483,37.42227033155257,100)
                .addToCoordinates(-122.0858169768481,37.42231408832346,100)
                .addToCoordinates(-122.085852582875,37.42230337469744,100)
                .addToCoordinates(-122.0858860101409,37.4222311076138,100)
                .addToCoordinates(-122.0858069157288,37.422202501003855,100)
                .addToCoordinates(-122.0858379542653,37.42214027058678,100)
                .addToCoordinates(-122.0856732640519,37.42208690214408,100)
                .addToCoordinates(-122.0856022926407,37.42214885429042,100)
                .addToCoordinates(-122.0855902778436,37.422128290487,100)
                .addToCoordinates(-122.0855841672237,37.422081001967246,100)
                .addToCoordinates(-122.0854852065741,37.42210455874995,100)
                .addToCoordinates(-122.0855067264352,37.42214267949824,100);




        //marshals to console
        kml.marshal();
        //marshals into file
        try {
            kml.marshal(new File("D:\\test-file\\generate-Kml-2.kml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void test03(){
        try {

            /**
             * google earth
             */
            CoordinateReferenceSystem sourceCRS = CRS.decode("EPSG:3785");
            CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:23032");

            DefaultGeographicCRS wgs84 = DefaultGeographicCRS.WGS84_3D;
            MathTransform transform = CRS.findMathTransform(sourceCRS, wgs84, false);

            Coordinate targetCoordinate = new Coordinate();
            Coordinate sourceCoordinate = new Coordinate();
            JTS.transform(sourceCoordinate, targetCoordinate, transform);



        } catch (FactoryException | TransformException e) {
            e.printStackTrace();
        }

    }

}