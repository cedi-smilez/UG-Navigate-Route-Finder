import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
    |--------------------------------------------------------------------------
    | MapUtils Class
    |--------------------------------------------------------------------------
    |
    | The MapUtils class provides utility methods for handling geographical locations
    | and landmarks. It includes methods to retrieve locations and landmarks,
    | calculate distances between them, find nearby landmarks, and calculate routes.
    |
    | Static Fields:
    | - LOCATIONS: A map of predefined locations with their names as keys and Location
    |   objects as values.
    | - LANDMARKS: A map of predefined landmarks with their names as keys and Location
    |   objects as values.
    |
    | Static Methods:
    | - getLocationByName(String name): Returns the Location object corresponding to
    |   the given name from the LOCATIONS map.
    | - getLandmarkByName(String name): Returns the Location object corresponding to
    |   the given name from the LANDMARKS map.
    | - calculateDistance(Location loc1, Location loc2): Calculates and returns the
    |   distance (in kilometers) between two Location objects using the Haversine formula.
    | - getNearbyLandmarks(Location currentLocation): Returns a list of landmarks within
    |   a 1 km radius of the given currentLocation.
    | - calculateRoute(Location start, Location end, Location landmark): Calculates
    |   a simple route from the start location to the end location, optionally passing
    |   through a specified landmark.
    |
    | This class is useful for applications involving mapping, navigation, and location
    | services where predefined locations and landmarks are used.
    |
    */
public class MapUtils {
    // Define locations and landmarks
    private static final Map<String, Location> LOCATIONS = new HashMap<>();
    private static final Map<String, Location> LANDMARKS = new HashMap<>();

    static {
        LOCATIONS.put("Main Entrance Gate", new Location("Main Entrance Gate", 5.6478, -0.1915));
        LOCATIONS.put("Legon Campus Library", new Location("Legon Campus Library", 5.6460, -0.1925));
        LOCATIONS.put("University of Ghana Medical School", new Location("University of Ghana Medical School", 5.6465, -0.1922));
        LOCATIONS.put("Centre for Digital Innovation and Entrepreneurship", new Location("Centre for Digital Innovation and Entrepreneurship", 5.6450, -0.1916));
        LOCATIONS.put("University Hostel", new Location("University Hostel", 5.6461, -0.1921));

        LANDMARKS.put("Great Hall", new Location("Great Hall", 5.6454, -0.1918));
        LANDMARKS.put("Sports Complex", new Location("Sports Complex", 5.6459, -0.1920));
        LANDMARKS.put("International House", new Location("International House", 5.6457, -0.1919));
    }

    public static Location getLocationByName(String name) {
        return LOCATIONS.get(name);
    }

    public static Location getLandmarkByName(String name) {
        return LANDMARKS.get(name);
    }

    public static double calculateDistance(Location loc1, Location loc2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(loc2.getLatitude() - loc1.getLatitude());
        double lonDistance = Math.toRadians(loc2.getLongitude() - loc1.getLongitude());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(loc1.getLatitude())) * Math.cos(Math.toRadians(loc2.getLatitude())) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in km
    }

    public static List<Location> getNearbyLandmarks(Location currentLocation) {
        List<Location> nearbyLandmarks = new ArrayList<>();
        for (Location location : LANDMARKS.values()) {
            if (calculateDistance(currentLocation, location) <= 1) { // 1 km radius
                nearbyLandmarks.add(location);
            }
        }
        return nearbyLandmarks;
    }

    public static List<Location> calculateRoute(Location start, Location end, Location landmark) {
        // Placeholder logic for route calculation
        List<Location> route = new ArrayList<>();

        // Add the start location
        route.add(start);

        // Add the landmark if not null
        if (landmark != null) {
            route.add(landmark);
        }

        // Add the end location
        route.add(end);

        return route;
    }
}
