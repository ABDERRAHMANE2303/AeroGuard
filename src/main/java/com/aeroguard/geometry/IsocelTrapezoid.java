package com.aeroguard.geometry;

// import org.locationtech.proj4j.CoordinateReferenceSystem;
// import org.locationtech.proj4j.CRSFactory;
// import org.locationtech.proj4j.CoordinateTransform;
// import org.locationtech.proj4j.CoordinateTransformFactory;
// import org.locationtech.proj4j.ProjCoordinate;

public class IsocelTrapezoid {
    private Point p1; 
    private Point p2; 
    private Point p3; 
    private Point p4; 
    private double length;
    private double baseDistance;
    private Line beginingLine;
    private Line endingLine;

    public IsocelTrapezoid(Point p1, Point p2, double length, double baseDistance) {
        if (p1 == null || p2 == null) {
            throw new IllegalArgumentException("Top right and top left corners cannot be null");
        }
        this.p1 = p1;
        this.p2 = p2;
        this.beginingLine=new Line(p1, p2);
        this.length = length;
        this.baseDistance = baseDistance;

        double bearing = calculateBearing(p1, p2);

        // Creates a rectangle initially then adjusts the width of the base to make it a trapez
        double width = p1.distanceToPoint(p2);
        double perpendicularBearing = (bearing + 90) % 360;
        this.p3 = offsetPoint(p2, length / 1000.0, perpendicularBearing);
        this.p4 = offsetPoint(p1, length / 1000.0, perpendicularBearing);
        double adjustment = (baseDistance - width) / 2;
        this.p3 = offsetPoint(p3, adjustment / 1000.0, bearing);
        this.p4 = offsetPoint(p4, -adjustment / 1000.0, bearing);
        
        this.endingLine=new Line(p3, p4);
    }

    private double calculateBearing(Point from, Point to) {
        double lat1 = Math.toRadians(from.getLatitude());
        double lat2 = Math.toRadians(to.getLatitude());
        double deltaLon = Math.toRadians(to.getLongitude() - from.getLongitude());

        double y = Math.sin(deltaLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) -
                Math.sin(lat1) * Math.cos(lat2) * Math.cos(deltaLon);
        return (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
    }

    // Offset point calculation considering bearing
    private Point offsetPoint(Point point, double distance, double bearing) {
        double R = 6371.0; // Radius of Earth in kilometers
        double bearingRad = Math.toRadians(bearing);

        double lat1 = Math.toRadians(point.getLatitude());
        double lon1 = Math.toRadians(point.getLongitude());

        double lat2 = Math.asin(Math.sin(lat1) * Math.cos(distance / R) +
                Math.cos(lat1) * Math.sin(distance / R) * Math.cos(bearingRad));
        double lon2 = lon1 + Math.atan2(Math.sin(bearingRad) * Math.sin(distance / R) * Math.cos(lat1),
                Math.cos(distance / R) - Math.sin(lat1) * Math.sin(lat2));
        lon2 = (lon2 + 3 * Math.PI) % (2 * Math.PI) - Math.PI; // Normalize to -180...+180

        return new Point(Math.toDegrees(lat2), Math.toDegrees(lon2), point.getElevation());
    }

    public IsocelTrapezoid rotateIsocelTrapezoid(double angleDegrees, Point rotationPoint) {
        // Rotate the top points (p1 and p2) around the rotation point
        Point rotatedTopLeft = p1.rotatePoint(rotationPoint, angleDegrees);
        Point rotatedTopRight = p2.rotatePoint(rotationPoint, angleDegrees);

        // Treate it as a rectangle and rotate the bottom points
        double originalBearing = calculateBearing(p1, p2);
        double newBearing = calculateBearing(rotatedTopLeft, rotatedTopRight);

        // Offset points to create the rectangle first
        Point rotatedBottomLeft = offsetPoint(rotatedTopLeft, length / 1000.0, (newBearing + 90) % 360);
        Point rotatedBottomRight = offsetPoint(rotatedTopRight, length / 1000.0, (newBearing + 90) % 360);

        // Adjust the bottom points to form the isosceles trapezoid
        double adjustment = (baseDistance - rotatedTopLeft.distanceToPoint(rotatedTopRight)) / 2;
        rotatedBottomRight = offsetPoint(rotatedBottomRight, adjustment / 1000.0, newBearing);
        rotatedBottomLeft = offsetPoint(rotatedBottomLeft, -adjustment / 1000.0, newBearing);

        // Create and return the new rotated trapezoid
        return new IsocelTrapezoid(rotatedTopLeft, rotatedTopRight, length, baseDistance);
    }

    public boolean isPointInside(Point point) {
        double DistanceUp=new Line(p1,p2).distanceToLine(point);
        double DistanceDown=new Line(p3,p4).distanceToLine(point);
        double DistanceRight=new Line(p1,p4).distanceToLine(point);
        double DistanceLeft=new Line(p2,p3).distanceToLine(point);
        double divergence=(baseDistance-(p1.distanceToPoint(p2)))/(2*length);
        double widthAtPoint=2*(DistanceUp*divergence)+(p1.distanceToPoint(p2));

        return DistanceUp<=length && DistanceDown<=length && DistanceRight<=widthAtPoint && DistanceLeft<=widthAtPoint;
    }

    public Point getP1() {
        return this.p1;
    }

    public Point getP2() {
        return p2;
    }

    public Point getP3() {
        return p3;
    }

    public Point getP4() {
        return p4;
    }

    public double getLength() {
        return length;
    }

    public double getBaseDistance() {
        return baseDistance;
    }

    public Line getbeginingLine(){
        return this.beginingLine;
    }
    public Line getendingLine(){
        return this.endingLine;
    }
}


















    // public static ProjCoordinate convertGPSToCartesian(double latitude, double longitude) {
    //     // Create a CRSFactory to define the coordinate systems
    //     CRSFactory crsFactory = new CRSFactory();

    //     // Define the source and target coordinate systems
    //     // EPSG:4326 is WGS84 (GPS coordinates), EPSG:3857 is Web Mercator
    //     CoordinateReferenceSystem sourceCRS = crsFactory.createFromName("EPSG:4326"); // WGS84
    //     CoordinateReferenceSystem targetCRS = crsFactory.createFromName("EPSG:3857"); // Mercator

    //     // Create a CoordinateTransformFactory to perform the transformation
    //     CoordinateTransformFactory transformFactory = new CoordinateTransformFactory();
    //     CoordinateTransform transform = transformFactory.createTransform(sourceCRS, targetCRS);

    //     // Input GPS coordinates
    //     ProjCoordinate gps = new ProjCoordinate(longitude, latitude); // (longitude, latitude)

    //     // Output Cartesian coordinates
    //     ProjCoordinate cartesian = new ProjCoordinate();

    //     // Perform the transformation
    //     transform.transform(gps, cartesian);

    //     return cartesian;
    // }



//    public boolean isPointInside(Point p) {
//        GeometryFactory geometryFactory = new GeometryFactory();
//
//        //convert polygon to cartesian
//        ProjCoordinate P1C = convertGPSToCartesian(this.p1.getLatitude(), this.p1.getLongitude());
//        ProjCoordinate P2C = convertGPSToCartesian(this.p2.getLatitude(), this.p2.getLongitude());
//        ProjCoordinate P3C = convertGPSToCartesian(this.p3.getLatitude(), this.p3.getLongitude());
//        ProjCoordinate P4C = convertGPSToCartesian(this.p4.getLatitude(), this.p4.getLongitude());
//        ProjCoordinate PCartesian = convertGPSToCartesian(p.getLatitude(),p.getLongitude());
//        // Create polygon with GPS coordinates
//        Coordinate[] coordinates = new Coordinate[] {
//                new Coordinate(P1C.x, P1C.y),
//                new Coordinate(P2C.x, P2C.y),
//                new Coordinate(P3C.x, P3C.y),
//                new Coordinate(P4C.x, P4C.y) ,
//                new Coordinate(P1C.x, P1C.y)
//        };
//        org.locationtech.jts.geom.Point jtsPoint = geometryFactory.createPoint(new Coordinate(PCartesian.x, PCartesian.y));
//
//        Polygon polygon = geometryFactory.createPolygon(coordinates);
//        boolean inside = polygon.contains(jtsPoint);
//        return inside;
//    }
