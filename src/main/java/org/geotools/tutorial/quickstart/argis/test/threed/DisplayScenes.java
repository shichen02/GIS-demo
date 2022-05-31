package org.geotools.tutorial.quickstart.argis.test.threed;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.ArcGISScene;
import com.esri.arcgisruntime.mapping.ArcGISTiledElevationSource;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.Surface;
import com.esri.arcgisruntime.mapping.view.Camera;
import com.esri.arcgisruntime.mapping.view.SceneView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author tsc
 * @description: TODO
 * @date 2022/5/31 15:47
 */
public class DisplayScenes extends Application {
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
}
