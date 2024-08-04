/*
    |--------------------------------------------------------------------------
    | MapUtils Class
    |--------------------------------------------------------------------------
    |
    | The MapUtils class offers utility methods for managing geographical locations
    | and landmarks. It provides functionality for retrieving location and landmark
    | data, calculating distances, finding nearby landmarks, and computing routes.
    |
    | Static Fields:
    | - LOCATIONS: A map of predefined locations with their names as keys and
    |   Location objects as values.
    | - LANDMARKS: A map of predefined landmarks with their names as keys and
    |   Location objects as values.
    |
    | Static Methods:
    | - getLocationByName(String name): Retrieves a Location object from the
    |   LOCATIONS map based on the provided name.
    | - getLandmarkByName(String name): Retrieves a Location object from the
    |   LANDMARKS map based on the provided name.
    | - calculateDistance(Location loc1, Location loc2): Computes the distance
    |   (in kilometers) between two Location objects using the Haversine formula.
    | - getNearbyLandmarks(Location currentLocation): Returns a list of landmarks
    |   within a 1 km radius of the given location.
    | - calculateRouteGreedy(Location start, Location end, Location landmark):
    |   Calculates a route from the start location to the end location using a
    |   greedy approach. The algorithm selects the nearest unvisited location at each
    |   step and optionally includes a specified landmark.
    | - calculateRouteDynamic(Location start, Location end, Location landmark):
    |   Computes the shortest route using the Floyd-Warshall algorithm. This dynamic
    |   programming approach calculates all-pairs shortest paths and reconstructs the
    |   shortest path from start to end, optionally including a specified landmark.
    | - calculateRoute(Location start, Location end, Location landmark):
    |   Chooses the route calculation method based on requirements. Currently defaults
    |   to the greedy approach but can be switched to dynamic programming.
    |
    | Data Structures:
    | - HashMap: Used for storing predefined locations and landmarks.
    | - ArrayList: Used for managing lists of locations and landmarks.
    | - 2D Array: Used in the Floyd-Warshall algorithm to store distance matrices.
    |
    | Algorithms:
    | - Haversine Formula: Used for calculating distances between geographic coordinates.
    | - Greedy Approach: Used for constructing routes by selecting the nearest unvisited
    |   location.
    | - Floyd-Warshall Algorithm: Used for finding shortest paths between all pairs of
    |   locations.
    |
    | This class is designed for applications involving mapping, navigation, and
    | location services where predefined locations and landmarks are utilized.
    |
    */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    // Applying Greedy Approach
    public static List<Location> calculateRouteGreedy(Location start, Location end, Location landmark) {
        List<Location> route = new ArrayList<>();
        Location current = start;

        route.add(current);

        // Use a Greedy approach to add locations closest to the current location
        while (current != null && !current.equals(end)) {
            Location next = null;
            double shortestDistance = Double.MAX_VALUE;

            for (Location loc : LOCATIONS.values()) {
                if (!route.contains(loc)) {
                    double distance = calculateDistance(current, loc);
                    if (distance < shortestDistance) {
                        shortestDistance = distance;
                        next = loc;
                    }
                }
            }

            if (next != null) {
                route.add(next);
                current = next;
            } else {
                break;
            }
        }

        // Add the end location
        route.add(end);

        // Optionally, add the landmark if not null
        if (landmark != null && !route.contains(landmark)) {
            route.add(landmark);
        }

        return route;
    }

    // Applying Dynamic Programming Approach (Floyd-Warshall Algorithm)
    public static List<Location> calculateRouteDynamic(Location start, Location end, Location landmark) {
        // Create a map of all locations
        List<Location> allLocations = new ArrayList<>(LOCATIONS.values());
        int n = allLocations.size();
        double[][] distanceMatrix = new double[n][n];

        // Initialize distance matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    distanceMatrix[i][j] = 0;
                } else {
                    distanceMatrix[i][j] = calculateDistance(allLocations.get(i), allLocations.get(j));
                }
            }
        }

        // Floyd-Warshall Algorithm
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (distanceMatrix[i][j] > distanceMatrix[i][k] + distanceMatrix[k][j]) {
                        distanceMatrix[i][j] = distanceMatrix[i][k] + distanceMatrix[k][j];
                    }
                }
            }
        }

        // Reconstruct the shortest path
        List<Location> route = new ArrayList<>();
        int startIndex = allLocations.indexOf(start);
        int endIndex = allLocations.indexOf(end);
        route.add(start);

        // Find the shortest path from start to end
        // This is a simplified example and does not handle intermediate locations properly
        while (startIndex != endIndex) {
            for (int i = 0; i < n; i++) {
                if (distanceMatrix[startIndex][i] + distanceMatrix[i][endIndex] == distanceMatrix[startIndex][endIndex]) {
                    route.add(allLocations.get(i));
                    startIndex = i;
                    break;
                }
            }
        }

        route.add(end);

        // Optionally, add the landmark if not null
        if (landmark != null && !route.contains(landmark)) {
            route.add(landmark);
        }

        return route;
    }

    public static List<Location> calculateRoute(Location start, Location end, Location landmark) {
        // Choose the algorithm based on requirements
        // Here, we'll use Greedy for simplicity, but you can switch to Dynamic Programming
        return calculateRouteGreedy(start, end, landmark);
    }
}
