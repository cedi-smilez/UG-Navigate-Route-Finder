/*
    |--------------------------------------------------------------------------
    | Location Class
    |--------------------------------------------------------------------------
    |
    | The Location class represents a geographical location with a name, latitude,
    | and longitude. This class is used to store and retrieve information about
    | specific points of interest on a map.
    |
    | Properties:
    | - name: The name of the location.
    | - latitude: The latitude coordinate of the location.
    | - longitude: The longitude coordinate of the location.
    |
    | Methods:
    | - getName(): Returns the name of the location.
    | - getLatitude(): Returns the latitude coordinate of the location.
    | - getLongitude(): Returns the longitude coordinate of the location.
    |
    | This class provides basic getters for accessing the location details and
    | is typically used in conjunction with mapping and routing functionality.
    |
    */
public class Location {
    private String name;
    private double latitude;
    private double longitude;

    public Location(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
