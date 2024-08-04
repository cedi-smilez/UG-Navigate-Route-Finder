package utils;

import java.util.HashMap;
import java.util.Map;
/*
    |--------------------------------------------------------------------------
    | AuthUtils Class
    |--------------------------------------------------------------------------
    |
    | The AuthUtils class provides utility methods for authenticating users within
    | the UG Navigate application. It contains a static map of student IDs and passwords
    | for authentication purposes. The class includes a method to verify if provided
    | credentials match the stored data.
    |
    | Static Block:
    | - Initializes the `studentAuth` map with mock student ID and password pairs for
    |   testing purposes.
    |
    | Methods:
    | - `authenticate(String studentId, String password)`:
    |   Checks if the provided student ID and password match the stored credentials.
    |   Returns true if the credentials are valid; otherwise, returns false.
    |
    | Fields:
    | - `studentAuth`: A map that stores student IDs as keys and corresponding passwords
    |   as values. Used for authentication checks.
    |
    | Features:
    | - Uses a static map to store authentication data for mock purposes.
    | - Provides a simple authentication mechanism by comparing the provided credentials
    |   with the stored data.
    |
    */

public class AuthUtils {
    private static final Map<String, String> studentAuth = new HashMap<>();

    static {
        studentAuth.put("1", "1"); // Mock data
        studentAuth.put("student456", "password456"); // Mock data
    }

    public static boolean authenticate(String studentId, String password) {
        return studentAuth.getOrDefault(studentId, "").equals(password);
    }
}
