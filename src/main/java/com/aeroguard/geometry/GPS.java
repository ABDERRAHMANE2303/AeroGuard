package com.aeroguard.geometry;


//This class could be used later to create a gps coordinates system converter 

public class GPS {

    // Convert decimal degrees to DMS (degrees, minutes, seconds)
    public static String decimalToDMS(double decimalDegree) {
        int degrees = (int) decimalDegree;
        double fractional = Math.abs(decimalDegree - degrees);
        int minutes = (int) (fractional * 60);
        double seconds = (fractional * 3600) - (minutes * 60);

        return degrees + "° " + minutes + "' " + String.format("%.2f", seconds) + "\"";
    }

    // Convert DMS to decimal degrees
    public static double dmsToDecimal(int degrees, int minutes, double seconds) {
        return degrees + (minutes / 60.0) + (seconds / 3600.0);
    }

    // Convert decimal degrees to degrees and decimal minutes (DDM)
    public static String decimalToDDM(double decimalDegree) {
        int degrees = (int) decimalDegree;
        double fractional = Math.abs(decimalDegree - degrees);
        double minutes = fractional * 60;

        return degrees + "° " + String.format("%.4f", minutes) + "'";
    }

    // Convert degrees and decimal minutes (DDM) to decimal degrees
    public static double ddmToDecimal(int degrees, double minutes) {
        return degrees + (minutes / 60.0);
    }

    // Convert DMS (degrees, minutes, seconds) to DDM (degrees and decimal minutes)
    public static String dmsToDDM(int degrees, int minutes, double seconds) {
        double totalMinutes = minutes + (seconds / 60.0);
        return degrees + "° " + String.format("%.4f", totalMinutes) + "'";
    }

    // Convert DDM (degrees and decimal minutes) to DMS (degrees, minutes, seconds)
    public static String ddmToDMS(int degrees, double minutes) {
        int intMinutes = (int) minutes;
        double fractionalMinutes = minutes - intMinutes;
        double seconds = fractionalMinutes * 60;

        return degrees + "° " + intMinutes + "' " + String.format("%.2f", seconds) + "\"";
    }

}
