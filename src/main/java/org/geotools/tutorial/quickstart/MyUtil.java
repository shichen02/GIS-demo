package org.geotools.tutorial.quickstart;

import org.geotools.coverage.util.FeatureUtilities;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.geotools.referencing.GeodeticCalculator;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.locationtech.jts.geom.Coordinate;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * @author tsc
 * @description: 空间地理的一些计算
 * @date 2022/5/19 14:09
 */
public class MyUtil {
    /**
     * 计算在 欧式几何 平面下多边形的面积，适用于空间直角坐标系
     *
     * @param polyPoints 围成多边形的点构成的集合
     * @return 面积
     */
    public double calculateTheArea(List<Point2D> polyPoints) {
        int size = polyPoints.size();
        Point2D[] params = new Point2D[size];
        for (int i = 0; i < size; i++) {
            params[i] = polyPoints.get(i);
        }
        return FeatureUtilities.area(params);
    }

    /**
     * 计算两个点在 欧氏几何 平面下的距离，适用于空间直角坐标系
     *
     * @param a 坐标1
     * @param b 坐标2
     * @return 距离
     */
    public double distance2D(Coordinate a, Coordinate b) {
        return a.distance(b);
    }

    /**
     * 计算两个点在 欧氏几何 空间下的距离，适用于空间直角坐标系
     *
     * @param a 坐标1
     * @param b 坐标2
     * @return 距离
     */
    public double distance3D(Coordinate a, Coordinate b) {
        return a.distance3D(b);
    }

    /**
     * 计算两个点的距离，适用于大地坐标系
     *
     * @param a 坐标1
     * @param b 坐标2
     * @return 距离
     */
    public double calculateDistance2D(Coordinate a, Coordinate b) {
        // 84坐标系构造GeodeticCalculator
        GeodeticCalculator geodeticCalculator = new GeodeticCalculator(DefaultGeographicCRS.WGS84);
        // 起点经纬度
        geodeticCalculator.setStartingGeographicPoint(a.getY(), a.getX());
        // 末点经纬度
        geodeticCalculator.setDestinationGeographicPoint(b.getY(), b.getX());
        // 计算距离，单位：米
        return geodeticCalculator.getOrthodromicDistance();
    }

    /**
     * 坐标转换,由于历史原因，GeoTools 无法自动判断何时返回与 EPSG 库一致的 CRS(Coordinate Reference System)，
     * 以及何时返回带有轴序配置的 CRS。所以 GeoTools 决定无论轴序是什么样的，
     * 都认为是和 EPSG 一致的 CRS，也就是 x 为纬度，y 为经度。
     */
    public void transfrom() throws FactoryException, TransformException {
        CoordinateReferenceSystem sourceCRS = CRS.decode("EPSG:4490");
        CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:4528");
        MathTransform mathTransform = CRS.findMathTransform(sourceCRS, targetCRS);
        Coordinate targetCoordinate = new Coordinate();
        Coordinate sourceCoordinate = new Coordinate();
        JTS.transform(sourceCoordinate, targetCoordinate, mathTransform);
    }


//    /**
//     * 解析 KML
//     */
//    void parseKML() throws FileNotFoundException {
//
//
//        String path = "C:\\Users\\Harrison\\Desktop\\work-document\\3.机场噪声项目\\geotools";
//        File file = new File(path);
//        InputStream inputStream = new FileInputStream(file);
//        StreamingParser parser = new StreamingParser(inputStream, KML.Placemark);
//        SimpleFeature f = null;
//
//        while ((f = (SimpleFeature) parser.parse()) != null) {
//            FeatureTypeStyle style = (FeatureTypeStyle) f.getAttribute("Style");
//
//            Symbolizer[] syms = style.getRules()[0].getSymbolizers();
//            assertEquals(3, syms.length);
//
//            count++;
//        }
//    }

//    public void ParseKML(String path) throws FileNotFoundException {
//        File source = new File(path);
//        InputStream fis = new FileInputStream(source);
//        PullParser parser = new PullParser(new KMLConfiguration(), fis, KML.Placemark);
//        SimpleFeature f = null;
//        features = new ArrayList<SimpleFeature>();
//        while ((f = (SimpleFeature) parser.parse()) != null) {
//            features.add(f);
//        }
//    }
}
