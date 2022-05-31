package org.geotools.tutorial.quickstart.argis.test;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author tsc
 * @date 2022/5/30 19:16
 */
public class App extends Application {

    private MapView mapView;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) {
        // set the title and size of the stage and show it
        stage.setTitle("Display a map tutorial");
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

        ArcGISMap map = new ArcGISMap(BasemapStyle.ARCGIS_TOPOGRAPHIC);

        // set the map on the map view
        mapView.setMap(map);

        mapView.setViewpoint(new Viewpoint(34.02700, -118.80543, 144447.638572));


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
        // create a polygon geometry from the point collection
        Polygon polygon = new Polygon(polygonPoints);

        // create an orange fill symbol with 20% transparency and the blue simple line symbol
        SimpleFillSymbol polygonFillSymbol =
                new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, 0x80FF5733, blueOutlineSymbol);
        // create a polygon graphic from the polygon geometry and symbol
        Graphic polygonGraphic = new Graphic(polygon, polygonFillSymbol);
        // add the polygon graphic to the graphics overlay
        graphicsOverlay.getGraphics().add(polygonGraphic);

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

}