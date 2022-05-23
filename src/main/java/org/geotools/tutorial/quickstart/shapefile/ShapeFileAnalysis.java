package org.geotools.tutorial.quickstart.shapefile;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tsc
 * @description: TODO
 * @date 2022/5/20 14:22
 */
public class ShapeFileAnalysis {

    static List<SimpleFeature> globalCollection = new ArrayList<>();

    static SimpleFeatureType LOCATION;

    static {
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("Location");
        builder.setCRS(DefaultGeographicCRS.WGS84); // <- Coordinate reference system
        // add attributes in order
        builder.add("the_geom", Polygon.class);
        builder.length(15).add("Name", String.class); // <- 15 chars width for name field
        builder.add("number", Integer.class);
        builder.add("Style", String.class);
        LOCATION = builder.buildFeatureType();
    }

    public static void main(String[] args) {
        test();
        try {
            write();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SchemaException e) {
            e.printStackTrace();
        }
    }


    public static void test() {
//        String path = "D:\\test-file\\shapefile\\geo-json\\geo-json.shp";
        String path = "D:\\test-file\\shapefile\\geo-json\\测试\\aaa_面.shp";
        File file = new File(path);

        HashMap<String, Object> map = new HashMap<>();
        try {
            map.put("url", file.toURI().toURL());
            DataStore dataStore = DataStoreFinder.getDataStore(map);
            String typeName = dataStore.getTypeNames()[0];

            SimpleFeatureSource source = dataStore.getFeatureSource(typeName);
            Filter include = Filter.INCLUDE;

            SimpleFeatureCollection collection = source.getFeatures(include);

            SimpleFeatureIterator features = collection.features();
            while (features.hasNext()) {
                SimpleFeature next = features.next();

                Collection<Property> properties = next.getProperties();

                properties.stream()
                        .forEach(e ->
                                System.out.println(e));
                System.out.println(next);

                globalCollection.add(next);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static List<SimpleFeature> getFeatures() throws SchemaException {
        List<SimpleFeature> features = new ArrayList<>();

        /*
         * GeometryFactory will be used to create the geometry attribute of each feature,
         * using a Point object for the location.
         */
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

        /**
         * 点
         */
//        final SimpleFeatureType TYPE = DataUtilities.createType("Location", "the_geom:Point:srid=4326," + "name:String," + "number:Integer");
//        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
        /* Longitude (= x coord) first ! */
//        Point point = geometryFactory.createPoint(new Coordinate(104.4385657305434, 30.2775382256351));
//        featureBuilder.add(point);
//        featureBuilder.add("name");
//        featureBuilder.add(1);
//        SimpleFeature feature = featureBuilder.buildFeature(null);

        /**
         * 图像，多边形
         */
        Coordinate a = new Coordinate(120.5521, 60.6667);
        Coordinate b = new Coordinate(121.5521, 60.6667);
        Coordinate c = new Coordinate(120.6921, 61.6667);
        Coordinate d = new Coordinate(122.6921, 61.6667);
        Coordinate e = new Coordinate(120.5521, 60.6667);
        ArrayList<Coordinate> objects = new ArrayList<>();
        objects.add(a);
        objects.add(b);
        objects.add(c);
        objects.add(d);
        objects.add(e);
        Coordinate[] coordinates = new Coordinate[5];
        for (int i = 0; i < 5; i++) {
            coordinates[i] = objects.get(i);
        }
        Polygon polygon = geometryFactory.createPolygon(coordinates);


        SimpleFeatureBuilder polygonBuilder = new SimpleFeatureBuilder(LOCATION);
        polygonBuilder.add(polygon);
        polygonBuilder.add("test");
        polygonBuilder.add(1);
        SimpleFeature simpleFeature = polygonBuilder.buildFeature(null);

        features.add(simpleFeature);

        return features;
    }


    public static void write() throws IOException, SchemaException {
        // 检查是否具有读写权限
        // 检查 shapefile 与 模板 （the simpleFeatureType TYPE）的匹配程度
        // 使用 simpleFeatureStore 用来执行此操作 需要一个 FeatureCollection 对象
        // 使用 transaction.commit 一次性安全地写出功能

//        final SimpleFeatureType TYPE = DataUtilities.createType("Location", "the_geom:Point:srid=4326," + "name:String," + "number:Integer");

        File newFile = new File("D:\\test-file\\shapefile\\geo-json\\generate8.shp");

        ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
        Map<String, Serializable> params = new HashMap<>();
        try {
            params.put("url", newFile.toURI().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        params.put("create spatial index", Boolean.TRUE);
        ShapefileDataStore newDataStore =
                (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);

        /*
         * TYPE is used as a template to describe the file contents
         */
        try {
            newDataStore.createSchema(LOCATION);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Transaction transaction = new DefaultTransaction("create");

        String typeName = newDataStore.getTypeNames()[0];
        // 父类 source 负责读和写
        SimpleFeatureSource featureSource = newDataStore.getFeatureSource(typeName);
//        SimpleFeatureType SHAPE_TYPE = featureSource.getSchema();
        if (featureSource instanceof SimpleFeatureStore) {
            // 子类 store 负责写
            SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;
            /*
             * SimpleFeatureStore has a method to add features from a
             * SimpleFeatureCollection object, so we use the ListFeatureCollection
             * class to wrap our list of features.
             */
            List<SimpleFeature> features = getFeatures();
            SimpleFeatureCollection collection = new ListFeatureCollection(LOCATION, globalCollection);
            featureStore.setTransaction(transaction);
            try {
                featureStore.addFeatures(collection);
                System.out.println("==============");
//                if (1==1){
//                    throw new RuntimeException("233");
//                }
                transaction.commit();
            } catch (Exception problem) {
                problem.printStackTrace();
                transaction.rollback();
            } finally {
                transaction.close();
            }
            System.exit(0); // success!
        } else {
            System.out.println(typeName + " does not support read/write access");
        }
    }


}
