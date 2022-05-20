//package org.geotools.tutorial.quickstart.shapefile;
//
//import com.vividsolutions.jts.geom.Coordinate;
//import org.locationtech.jts.geom.Coordinate;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author 许利钢
// * @description
// * @date 2022年05月19日 18:08
// */
//public class ShapeProperty {
//
//    private String name;
//
//    private String style;
//
//    /**
//     * 点、线、面：点信息通过Coordinates获取
//     * 其它时：通过others中的“geom”键值获取
//     */
//    private String type;
//
//    private List<Coordinate> coordinates;
//
//    private Map<String, Object> others;
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getStyle() {
//        return style;
//    }
//
//    public void setStyle(String style) {
//        this.style = style;
//    }
//
//    /**
//     * 点、线、面：点信息通过Coordinates获取
//     * 其它时：通过others属性中的“geom”键值获取
//     * @return point:点，line：线，polygon:面，geom:其它
//     */
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public List<Coordinate> getCoordinates() {
//        return coordinates;
//    }
//
//    public void setCoordinates(List<Coordinate> coordinates) {
//        this.coordinates = coordinates;
//    }
//
//    public void setCoordinatesByArr(Coordinate[] coordinates){
//        this.coordinates = Arrays.asList(coordinates);
//    }
//
//    public Map<String, Object> getOthers() {
//        if(others == null){
//            return new HashMap<String, Object>(4);
//        }
//        return others;
//    }
//
//    public void setOthers(Map<String, Object> others) {
//        this.others = others;
//    }
//}
