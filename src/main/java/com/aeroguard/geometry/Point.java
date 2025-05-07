package com.aeroguard.geometry;

public class Point {
    private double latitude;
    private double longitude;
    private double elevation;

    public Point(double latitude, double longitude, double elevation) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
    }

    // Haversine formula 
    public double distanceToPoint(Point other) {
        final double R = 6371000; // Earth radius in meters

        double latDistance = Math.toRadians(other.getLatitude() - this.latitude);
        double lonDistance = Math.toRadians(other.getLongitude() - this.longitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(other.getLatitude())) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;// Horizontal distance in meters

//        return distance;

        //Calculate the 3D distance including elevation(mostly unused to simplify calculations), not really needed in our case .
        //most constructions are withing a small area and the elevation difference is negligible .
        double elevationDifference = other.getElevation() - this.getElevation();
        return Math.sqrt(distance * distance + elevationDifference * elevationDifference);
    }


    // Offset the point by a certain distance in meters(useful for transformations)
    public Point offsetPoint(double northOffsetMeters, double eastOffsetMeters) {
        double metersPerDegreeLat = 111320;
        double metersPerDegreeLon = 40075000 * Math.cos(Math.toRadians(this.latitude)) / 360;

        double newLat = this.latitude + (northOffsetMeters / metersPerDegreeLat);
        double newLon = this.longitude + (eastOffsetMeters / metersPerDegreeLon);

        return new Point(newLat, newLon, this.elevation);
    }
    

    public Point rotatePoint(Point center, double angleDegrees) {
        double angleRadians = Math.toRadians(angleDegrees);

        // Translate point back to the origin (center)
        double translatedX = this.getLatitude() - center.getLatitude();
        double translatedY = this.getLongitude() - center.getLongitude();

        // Perform the rotation around the origin (0,0)
        double rotatedX = translatedX * Math.cos(angleRadians) - translatedY * Math.sin(angleRadians);
        double rotatedY = translatedX * Math.sin(angleRadians) + translatedY * Math.cos(angleRadians);

        // Translate point back to its original position
        double newLat = rotatedX + center.getLatitude();
        double newLon = rotatedY + center.getLongitude();

        return new Point(newLat, newLon, this.getElevation());
    }


    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getElevation() {
        return elevation;
    }
}





















//    public double distanceToPoint( Point point2) {
//        // Create CRSFactory to get Coordinate Reference Systems (CRS)
//        CRSFactory crsFactory = new CRSFactory();
//        CoordinateReferenceSystem wgs84 = crsFactory.createFromName("EPSG:4326"); // WGS84 (lat, lon)
//        CoordinateReferenceSystem utm = crsFactory.createFromName("EPSG:32633");  // UTM Zone 33N, change for your region
//
//        // Create CoordinateTransformFactory
//        CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
//
//        // Create the transformation from WGS84 (lat, lon) to UTM
//        CoordinateTransform transform = ctFactory.createTransform(wgs84, utm);
//
//        // Create ProjCoordinate objects for the two points
//        ProjCoordinate srcCoord1 = new ProjCoordinate(this.getLongitude(), this.getLatitude());
//        ProjCoordinate destCoord1 = new ProjCoordinate();
//        transform.transform(srcCoord1, destCoord1); // Transforms point1 to UTM
//
//        ProjCoordinate srcCoord2 = new ProjCoordinate(point2.getLongitude(), point2.getLatitude());
//        ProjCoordinate destCoord2 = new ProjCoordinate();
//        transform.transform(srcCoord2, destCoord2); // Transforms point2 to UTM
//
//        // Now destCoord1 and destCoord2 are in Cartesian UTM coordinates (meters)
//        // Calculate the Euclidean distance between the two points
//        double deltaX = destCoord2.x - destCoord1.x;
//        double deltaY = destCoord2.y - destCoord1.y;
//
//        // Calculate the horizontal distance
//        double horizontalDistance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
//
//        // Include elevation difference for 3D distance if needed
//        double elevationDifference = point2.getElevation() - this.getElevation();
//        return Math.sqrt(horizontalDistance * horizontalDistance + elevationDifference * elevationDifference);
//    }  