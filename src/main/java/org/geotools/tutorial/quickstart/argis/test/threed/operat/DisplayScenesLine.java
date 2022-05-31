package org.geotools.tutorial.quickstart.argis.test.threed.operat;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.ArcGISScene;
import com.esri.arcgisruntime.mapping.ArcGISTiledElevationSource;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.Surface;
import com.esri.arcgisruntime.mapping.view.Camera;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.SceneView;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;

/**
 * @author tsc
 * @description: TODO
 * @date 2022/5/31 15:47
 */
public class DisplayScenesLine extends Application {
    private SceneView sceneView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // set the title and size of the stage and show it
        stage.setTitle("Display a scene tutorial");
        stage.setWidth(800);
        stage.setHeight(700);
        stage.show();

        // create a JavaFX scene with a stack pane as the root node, and add it to the scene
        StackPane stackPane = new StackPane();
        Scene fxScene = new Scene(stackPane);

        stage.setScene(fxScene);
        String yourApiKey = "AAPK880fdddbf98444aabc049afc2b20600bC8T1OjSlRmKUfIEua7gHnzHdlPZsLruDns8KAKEssuUABa5bShvcWtnKWeU0Ud6f";
        ArcGISRuntimeEnvironment.setApiKey(yourApiKey);


        // create a scene view to display the scene and add it to the stack pane
        sceneView = new SceneView();
        stackPane.getChildren().add(sceneView);


        ArcGISScene scene = new ArcGISScene(Basemap.createImagery());

        // set the scene on the scene view
        sceneView.setArcGISScene(scene);

        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        sceneView.getGraphicsOverlays().add(graphicsOverlay);

        // add base surface for elevation data
        Surface surface = new Surface();
        String elevationServiceUrl = "https://elevation3d.arcgis.com/arcgis/rest/services/WorldElevation3D/Terrain3D/ImageServer";
        surface.getElevationSources().add(new ArcGISTiledElevationSource(elevationServiceUrl));
        // add an exaggeration factor to increase the 3D effect of the elevation.
        surface.setElevationExaggeration(2.5f);

        scene.setBaseSurface(surface);

        Point cameraLocation = new Point(-118.794, 33.909, 5330.0, SpatialReferences.getWgs84());
        Camera camera = new Camera(cameraLocation, 355.0, 72.0, 0.0);
        sceneView.setViewpointCamera(camera);

        PointCollection polygonPoints = new PointCollection(SpatialReferences.getWgs84());
        polygonPoints.add(new Point(-118.818984489994, 34.0137559967283,200));
        polygonPoints.add(new Point(-118.806796597377, 34.0215816298725,300));
        polygonPoints.add(new Point(-118.791432890735, 34.0163883241613,7000));
        polygonPoints.add(new Point(-118.795966865355, 34.0085648646355,500));
        polygonPoints.add(new Point(-118.808558110679, 34.0035027131376,10000));

        PointCollection shipPoints = createShipPoints();
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

        Coordinate[] coords = new Coordinate[]{
                new Coordinate(2330611.130549, 202360.002957, 0.000000),
                new Coordinate(2330583.834672, 202525.984012, 0.000000),
                new Coordinate(2330574.164902, 202691.488009, 0.000000),
                new Coordinate(2330696.773344, 203317.495798, 0.000000),
        };

        org.locationtech.jts.geom.Geometry polygon = geometryFactory.createLineString(coords);
        Geometry smooth = JTS.smooth(polygon, 1);
        Coordinate[] coordinates = smooth.getCoordinates();

        Polyline polyline = new Polyline(polygonPoints);

        Graphic polylineGraphic1 = new Graphic(polyline, new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, 0xFF00FFFF,
                3));
        graphicsOverlay.getGraphics().add(polylineGraphic1);
    }

    /**
     * Stops and releases all resources used in application.
     */
    @Override
    public void stop() {
        if (sceneView != null) {
            sceneView.dispose();
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
