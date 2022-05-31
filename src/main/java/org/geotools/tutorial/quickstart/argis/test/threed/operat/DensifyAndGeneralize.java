package org.geotools.tutorial.quickstart.argis.test.threed.operat;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Multipoint;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;

/**
 * @author tsc
 * @description: TODO
 * @date 2022/5/31 16:17
 */
public class DensifyAndGeneralize {
    @FXML
    private MapView mapView;
    @FXML private Slider maxSegmentLengthSlider;
    @FXML private Slider maxDeviationSlider;
    @FXML private CheckBox resultVisibilityCheckBox;
    @FXML private CheckBox densifyCheckBox;
    @FXML private CheckBox generalizeCheckBox;

    private Polyline originalPolyline;
    private Graphic resultPointsGraphic;
    private Graphic resultPolylineGraphic;

    @FXML
    public void initialize() {
        // authentication with an API key or named user is required to access basemaps and other location services
        String yourApiKey = "AAPK880fdddbf98444aabc049afc2b20600bC8T1OjSlRmKUfIEua7gHnzHdlPZsLruDns8KAKEssuUABa5bShvcWtnKWeU0Ud6f";
        ArcGISRuntimeEnvironment.setApiKey(yourApiKey);

        // create a map with the streets night basemap style
        ArcGISMap map = new ArcGISMap(BasemapStyle.ARCGIS_STREETS_NIGHT);

        // set the map to the map view
        MapView mapView = new MapView();

        mapView.setMap(map);

        // create a graphics overlay
        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        mapView.getGraphicsOverlays().add(graphicsOverlay);

        // create some points along a river for the original geometry
        PointCollection points = createShipPoints();

        // show the original points as red dots on the map
        Multipoint originalMultipoint = new Multipoint(points);
        Graphic originalPointsGraphic = new Graphic(originalMultipoint, new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE,
                0xFFFF0000, 7));
        graphicsOverlay.getGraphics().add(originalPointsGraphic);

        // show a dotted red line connecting the original points
        originalPolyline = new Polyline(points);
        Graphic originalPolylineGraphic = new Graphic(originalPolyline, new SimpleLineSymbol(SimpleLineSymbol.Style.DOT,
                0xFFFF0000, 3));
        graphicsOverlay.getGraphics().add(originalPolylineGraphic);

        // show the result (densified and generalized) points as magenta dots on the map
        resultPointsGraphic = new Graphic();
        resultPointsGraphic.setSymbol(new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, 0xFFFF00FF, 7));
        graphicsOverlay.getGraphics().add(resultPointsGraphic);

        // connect the result points with a magenta line
        resultPolylineGraphic = new Graphic();
        resultPolylineGraphic.setSymbol(new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, 0xFFFF00FF, 3));
        graphicsOverlay.getGraphics().add(resultPolylineGraphic);

        // update the densified/generalized parameters when the slider values change
        maxSegmentLengthSlider.valueProperty().addListener(o -> updateGeometry());
        maxDeviationSlider.valueProperty().addListener(o -> updateGeometry());

        // set initial values
        maxSegmentLengthSlider.setValue(100);
        maxDeviationSlider.setValue(10);

        // zoom to the original polyline
        mapView.setViewpointGeometryAsync(originalPolyline.getExtent(), 100);
    }

    /**
     * Called when any of the densify/generalize option values are changed. Applies the densify and generalize
     * operations to the original polyline and updates the result graphics with the result geometry.
     */
    @FXML
    private void updateGeometry() {
        Polyline polyline = originalPolyline;
        if (generalizeCheckBox.isSelected()) {
            polyline = (Polyline) GeometryEngine.generalize(polyline, maxDeviationSlider.getValue(), true);
        }
        if (densifyCheckBox.isSelected()) {
            polyline = (Polyline) GeometryEngine.densify(polyline, maxSegmentLengthSlider.getValue());
        }
        resultPolylineGraphic.setGeometry(polyline);
        Multipoint multipoint = new Multipoint(polyline.getParts().getPartsAsPoints());
        resultPointsGraphic.setGeometry(multipoint);
    }

    /**
     * Toggles the visibility of the result graphics depending on the state of the result visibility checkbox.
     */
    @FXML
    private void updateResultVisibility() {
        resultPolylineGraphic.setVisible(resultVisibilityCheckBox.isSelected());
        resultPointsGraphic.setVisible(resultVisibilityCheckBox.isSelected());
    }

    /**
     * Creates a collection of points along the Willamette River in Portland, OR.
     *
     * @return points
     */
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

    /**
     * Stops and releases all resources used in application.
     */
    void terminate() {

        if (mapView != null) {
            mapView.dispose();
        }
    }
}
