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
import com.esri.arcgisruntime.portal.Portal;
import com.esri.arcgisruntime.portal.PortalItem;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author tsc
 * @description: TODO
 * @date 2022/5/31 15:47
 */
public class DisplayWebScenes extends Application {
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


        Portal portal = new Portal("http://www.arcgis.com/", false);
        PortalItem portalItem = new PortalItem(portal, "579f97b2f3b94d4a8e48a5f140a6639b");
        ArcGISScene scene = new ArcGISScene(portalItem);

        // set the scene on the scene view
        sceneView.setArcGISScene(scene);


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
