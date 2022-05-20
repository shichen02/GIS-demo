//package org.geotools.tutorial.quickstart.shapefile;
//
//import com.bsac.myselftools.huanghua.SensitivePointInfo;
//import com.vividsolutions.jts.geom.*;
//import org.geotools.data.FeatureSource;
//import org.geotools.data.FeatureWriter;
//import org.geotools.data.Transaction;
//import org.geotools.data.shapefile.ShapefileDataStore;
//import org.geotools.feature.FeatureCollection;
//import org.geotools.feature.FeatureIterator;
//import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
//import org.geotools.referencing.crs.DefaultGeographicCRS;
//import org.locationtech.jts.geom.MultiLineString;
//import org.opengis.feature.Property;
//import org.opengis.feature.simple.SimpleFeature;
//import org.opengis.feature.simple.SimpleFeatureType;
//import org.opengis.geometry.coordinate.GeometryFactory;
//import org.opengis.geometry.primitive.Point;
//import org.opengis.referencing.crs.CoordinateReferenceSystem;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
///**
// * @author 许利钢
// * @description
// * @date 2022年05月19日 11:17
// */
//public class ShapeFileUtil {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(ShapeFileUtil.class);
//
//    public static final String DEFAULT_WKT_ID = "4326";
//
//    public static final String WORLD_MERCATOR_WKT_ID = "3395";
//
//    public static final String WEB_MERCATOR_WKT_ID = "3857";
//
//    private static final String WGS84 = "WGS_84_World_Mercator";
//
//    private static final String WGS1984 = "WGS_1984_Web_Mercator_Auxiliary_Sphere";
//
//
//    private static final String DEFAULT_ENCODING = "UTF-8";
//
//    public List<ShapeProperty> readShapeFile(String filePath) {
//        //创建shape文件对象
//        File file = new File(filePath);
//        if (!file.exists()) {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                LOGGER.error("Create file error! path->:{}",file.getAbsolutePath(), e);
//            }
//        }
//        return readFile(file);
//    }
//
//    public List<ShapeProperty> readShapeFile(File file) {
//        return readFile(file);
//    }
//
////    public List<ShapeProperty> readShapeFile(MultipartFile shapeFile, File tmpFile) {
////        OutputStream outputStream = null;
////        try {
////            byte[] dataBytes = shapeFile.getBytes();
////            outputStream = new FileOutputStream(tmpFile);
////            outputStream.write(dataBytes);
////        } catch (IOException e) {
////            LOGGER.error("IO error.",e);
////        } finally {
//////            closeStream(outputStream);
////        }
////        return readFile(tmpFile);
////    }
//
//
//    private static List<ShapeProperty> readFile(File file) {
//        List<ShapeProperty> dataList = new ArrayList<>();
//        try {
//            ShapefileDataStore shapefileDataStore = new ShapefileDataStore(file.toURI().toURL());
//            shapefileDataStore.setCharset(Charset.forName("GBK"));
//            String typeName = shapefileDataStore.getTypeNames()[0];
//            FeatureSource<SimpleFeatureType, SimpleFeature> featureSource =
//                    shapefileDataStore.getFeatureSource(typeName);
//            FeatureCollection<SimpleFeatureType, SimpleFeature> result = featureSource.getFeatures();
//            FeatureIterator<SimpleFeature> iterator = result.features();
//            while (iterator.hasNext()) {
//                SimpleFeature feature = iterator.next();
//                Collection<Property> p = feature.getProperties();
//                ShapeProperty shapeProperty = new ShapeProperty();
//                Map<String, Object> map = new HashMap<>(16);
//                for (Property pro : p) {
//                    if ("the_geom".equals(pro.getName().toString())) {
//                        if (pro.getValue() instanceof Point) {
//                            shapeProperty.setType("point");
//                            shapeProperty.setCoordinatesByArr(((Point) pro.getValue()).getCoordinates());
//                            map.put("point", shapeProperty.getCoordinates());
//                        } else if (pro.getValue() instanceof MultiLineString) {
//                            shapeProperty.setType("line");
//                            shapeProperty.setCoordinatesByArr(((MultiLineString) pro.getValue()).getCoordinates());
//                            map.put("line", shapeProperty.getCoordinates());
//                        } else if (pro.getValue() instanceof MultiPolygon) {
//                            shapeProperty.setType("polygon");
//                            shapeProperty.setCoordinatesByArr(((MultiPolygon) pro.getValue()).getCoordinates());
//                            map.put("polygon", shapeProperty.getCoordinates());
//                        } else {
//                            map.put("geom", pro.getValue());
//                        }
//                    } else if ("Name".equals(pro.getName().toString())) {
//                        shapeProperty.setName(pro.getValue().toString());
//                    } else if ("Style".equals(pro.getName().toString())) {
//                        shapeProperty.setStyle(pro.getValue().toString());
//                    } else {
//                        map.put(pro.getName().toString(), pro.getValue());
//                    }
//                }
//                shapeProperty.setOthers(map);
//                dataList.add(shapeProperty);
//            }
//            iterator.close();
//            shapefileDataStore.dispose();
//        } catch (Exception e) {
//            LOGGER.error("Read shape file error!", e);
//        }
//        return dataList;
//    }
//
//
//    private static void writeFile(String filepath, List<SensitivePointInfo> pointInfoList) {
//        try {
//            //创建shape文件对象
//            File file = new File(filepath);
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//            ShapefileDataStore ds = new ShapefileDataStore(file.toURI().toURL());
//            //定义图形信息和属性信息
//            SimpleFeatureTypeBuilder tb = new SimpleFeatureTypeBuilder();
//            tb.setCRS(DefaultGeographicCRS.WGS84);
//            tb.setName("SensitivePoint");
//            tb.add("the_geom", Point.class);
//            tb.add("NO", String.class);
//            tb.add("NAME", String.class);
//            ds.createSchema(tb.buildFeatureType());
//            ds.setCharset(Charset.forName("GBK"));
//            GeometryFactory geometryFactory = new GeometryFactory();
//            //设置Writer
//            FeatureWriter<SimpleFeatureType, SimpleFeature> writer = ds.getFeatureWriter(ds.getTypeNames()[0],
//                    Transaction.AUTO_COMMIT);
//            for (SensitivePointInfo pointInfo : pointInfoList) {
//                //写下一条
//                SimpleFeature feature = writer.next();
//                feature.setAttribute("the_geom", geometryFactory.createPoint(new Coordinate(pointInfo.getLongitude(),
//                        pointInfo.getLatitude())));
//                feature.setAttribute("NO", pointInfo.getNo());
//                feature.setAttribute("NAME", pointInfo.getName());
//            }
//            writer.write();
//            writer.close();
//            ds.dispose();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//
//    public static void main(String[] args) {
//
////        String path = "E:\\资料\\项目开发\\成都天府国际机场\\等值线\\等值线\\天府机场等值线_dwg_Polyline\\天府机场等值线_dwg_Polyline.shx";
////
////        //创建shape文件对象
////        File file = new File(path);
////        try {
////            file.createNewFile();
////        } catch (Exception e) {
////            LOGGER.error("Create file error! path->:{}",file.getAbsolutePath(), e);
////        }
////        if (!file.exists()) {
////        }
////        readFile(file);
//
////        List<String> list = List.of("sss","aaa","ddd");
//        System.out.println("sssssssssssss");
//    }
//
//}
