package org.geotools.tutorial.quickstart.argis.test.threed.operat;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Multipoint;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.internal.jni.CoreGeometryDimension;
import com.esri.arcgisruntime.internal.jni.CorePolygon;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.portal.Portal;
import com.esri.arcgisruntime.portal.PortalItem;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.google.common.hash.HashingOutputStream;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author tsc
 * @date 2022/5/30 19:16
 */
public class DisplayWebDensifyAndGeneralizeMap extends Application {

    private MapView mapView;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) {
        // set the title and size of the stage and show it
        stage.setTitle("Display a web map tutorial");
        stage.setWidth(800);
        stage.setHeight(700);
        stage.show();

        // create a JavaFX scene with a stack pane as the root node, and add it to the scene
        StackPane stackPane = new StackPane();
        Scene scene = new Scene(stackPane);
        stage.setScene(scene);

        // Note: it is not best practice to store API keys in source code.
        // The API key is referenced here for the convenience of this tutorial.
        String yourApiKey = "AAPK880fdddbf98444aabc049afc2b20600bC8T1OjSlRmKUfIEua7gHnzHdlPZsLruDns8KAKEssuUABa5bShvcWtnKWeU0Ud6f";
        ArcGISRuntimeEnvironment.setApiKey(yourApiKey);

        // create a map view to display the map and add it to the stack pane
        mapView = new MapView();
        stackPane.getChildren().add(mapView);

        Portal portal = new Portal("https://www.arcgis.com", false);
        String itemId = "41281c51f9de45edaf1c8ed44bb10e30";
        PortalItem portalItem = new PortalItem(portal, itemId);
        ArcGISMap map = new ArcGISMap(portalItem);

        // set the map on the map view
        mapView.setMap(map);




        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        mapView.getGraphicsOverlays().add(graphicsOverlay);
        Point point = new Point(-118.80657463861, 34.0005930608889, SpatialReferences.getWgs84());
        // create an opaque orange (0xFFFF5733) point symbol with a blue (0xFF0063FF) outline symbol
        SimpleMarkerSymbol simpleMarkerSymbol =
                new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, 0xFFFF5733, 10);
        SimpleLineSymbol blueOutlineSymbol =
                new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, 0xFF0063FF, 2);
        simpleMarkerSymbol.setOutline(blueOutlineSymbol);

        // create a graphic with the point geometry and symbol
        Graphic pointGraphic = new Graphic(point, simpleMarkerSymbol);
        // add the point graphic to the graphics overlay
        graphicsOverlay.getGraphics().add(pointGraphic);


        // create a point collection with a spatial reference, and add five points to it
        PointCollection polygonPoints = new PointCollection(SpatialReferences.getWgs84());
        polygonPoints.add(new Point(-118.818984489994, 34.0137559967283));
        polygonPoints.add(new Point(-118.806796597377, 34.0215816298725));
        polygonPoints.add(new Point(-118.791432890735, 34.0163883241613));
        polygonPoints.add(new Point(-118.795966865355, 34.0085648646355));
        polygonPoints.add(new Point(-118.808558110679, 34.0035027131376));

        Polyline polyline = new Polyline(polygonPoints);

        // show the original points as red dots on the map
        Multipoint originalMultipoint = new Multipoint(polygonPoints);
        Graphic originalPointsGraphic = new Graphic(originalMultipoint, new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE,
                0xFFFF0000, 7));
        graphicsOverlay.getGraphics().add(originalPointsGraphic);

        // create a polygon geometry from the point collection
        Geometry densify = GeometryEngine.densify(polyline, 0.001);


        // create an orange fill symbol with 20% transparency and the blue simple line symbol
        SimpleFillSymbol polygonFillSymbol =
                new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, 0x80FF5733, blueOutlineSymbol);
        // create a polygon graphic from the polygon geometry and symbol
        Graphic polygonGraphic = new Graphic(polyline, polygonFillSymbol);
        Graphic polylineGraphic = new Graphic(densify, new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, 0xFF00FFFF,
                3));
        // add the polygon graphic to the graphics overlay
        graphicsOverlay.getGraphics().add(polygonGraphic);
        graphicsOverlay.getGraphics().add(polylineGraphic);


        PointCollection shipPoints = createShipPoints();
        Polyline polyline1 = new Polyline(shipPoints);
        Graphic polylineGraphic1 = new Graphic(polyline1, new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, 0xFF00FFFF,
                3));
        graphicsOverlay.getGraphics().add(polylineGraphic1);

    }

    /**
     * Stops and releases all resources used in application.
     */
    @Override
    public void stop() {
        if (mapView != null) {
            mapView.dispose();
        }
    }

    private PointCollection createShipPoints() {
        PointCollection points = new PointCollection(SpatialReference.create(32126));
        points.add(new Point(2330611.130549, 202360.002957, 0.000000));
        points.add(new Point(2330583.834672, 202525.984012, 0.000000));
        points.add(new Point(2330574.164902, 202691.488009, 0.000000));
        points.add(new Point(2330689.292623, 203170.045888, 0.000000));
        points.add(new Point(2330696.773344, 203317.495798, 0.000000));
        points.add(new Point(2330691.419723, 203380.917080, 0.000000));
        points.add(new Point(2330435.065296, 203816.662457, 0.000000));
        points.add(new Point(2330369.500800, 204329.861789, 0.000000));
        points.add(new Point(2330400.929891, 204712.129673, 0.000000));
        points.add(new Point(2330484.300447, 204927.797132, 0.000000));
        points.add(new Point(2330514.469919, 205000.792463, 0.000000));
        points.add(new Point(2330638.099138, 205271.601116, 0.000000));
        points.add(new Point(2330725.315888, 205631.231308, 0.000000));
        points.add(new Point(2330755.640702, 206433.354860, 0.000000));
        points.add(new Point(2330680.644719, 206660.240923, 0.000000));
        points.add(new Point(2330386.957926, 207340.947204, 0.000000));
        points.add(new Point(2330485.861737, 207742.298501, 0.000000));
        return points;
    }

}